package org.laxio.piston.protocol.v340.session;

        /*-
         * #%L
         * Protocol
         * %%
         * Copyright (C) 2017 Laxio
         * %%
         * This file is part of Piston, licensed under the MIT License (MIT).
         *
         * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
         *
         * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
         *
         * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
         * #L%
         */

import org.json.JSONObject;
import org.laxio.piston.piston.PistonServer;
import org.laxio.piston.piston.exception.PistonRuntimeException;
import org.laxio.piston.piston.exception.protocol.auth.SessionAuthenticationException;
import org.laxio.piston.piston.session.MinecraftSessionService;
import org.laxio.piston.piston.session.Profile;
import org.laxio.piston.piston.session.SessionResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class MojangSessionService implements MinecraftSessionService {

    private static final String BASE_URL = "https://sessionserver.mojang.com/session/minecraft/";

    private static final URL CHECK_URL;

    static {
        try {
            CHECK_URL = new URL(BASE_URL + "hasJoined");
        } catch (Exception ex) {
            throw new PistonRuntimeException(new SessionAuthenticationException("Unable to build CHECK_URL", ex));
        }
    }

    private final PistonServer server;

    public MojangSessionService(PistonServer server) {
        this.server = server;
    }

    public PistonServer getServer() {
        return server;
    }

    @Override
    public SessionResponse hasJoined(Profile profile, String serverId) throws SessionAuthenticationException {
        return hasJoined(profile, serverId, null);
    }

    @Override
    public SessionResponse hasJoined(Profile profile, String serverId, String ip) throws SessionAuthenticationException {
        try {
            URL request = build(profile, serverId, ip);
            HttpURLConnection con = (HttpURLConnection) request.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String inputLine;
            StringBuilder builder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                builder.append(inputLine);
            }

            in.close();
            String content = builder.toString();
            if (con.getResponseCode() == 204) {
                throw new SessionAuthenticationException("Response code 204 - invalid request");
            }


            JSONObject json = new JSONObject(content);
            String uuid = json.getString("id").replaceAll(
                    "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                    "$1-$2-$3-$4-$5");

            return new SessionResponse(json.getString("name"), UUID.fromString(uuid));
        } catch (Exception ex) {
            throw new SessionAuthenticationException("Unable to check join", ex);
        }
    }

    private URL build(Profile profile, String serverId, String ip) throws MalformedURLException {
        StringBuilder url = new StringBuilder(CHECK_URL.toString());
        url.append("?username=");
        url.append(profile.getName());
        url.append("&serverId=");
        url.append(serverId);
        if (ip != null && ip.length() > 0) {
            url.append("&ip=");
            url.append(ip);
        }

        return new URL(url.toString());
    }

}
