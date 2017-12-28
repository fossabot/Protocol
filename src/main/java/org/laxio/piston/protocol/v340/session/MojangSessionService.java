package org.laxio.piston.protocol.v340.session;

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
import java.util.logging.Logger;

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

    @Override
    public SessionResponse hasJoined(Profile profile, String serverId) throws SessionAuthenticationException {
        return hasJoined(profile, serverId, server.getBindAddress().getAddress().getHostAddress());
    }

    @Override
    public SessionResponse hasJoined(Profile profile, String serverId, String ip) throws SessionAuthenticationException {
        try {
            URL request = build(profile, serverId, ip);
            Logger.getGlobal().info(request.toString());

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
            Logger.getGlobal().info(json.toString(2));

            String uuid = json.getString("id").replaceAll(
                    "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                    "$1-$2-$3-$4-$5");

            return new SessionResponse(json.getString("name"), UUID.fromString(uuid));
        } catch (Exception ex) {
            throw new SessionAuthenticationException("Unable to check join", ex);
        }
    }

    private URL build(Profile profile, String serverId, String ip) throws MalformedURLException {
        return new URL(CHECK_URL.toString()
                + "?username=" + profile.getName()
                + "&serverId=" + serverId);
    }

}
