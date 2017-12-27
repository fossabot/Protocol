package org.laxio.piston.protocol.v340.session;

import org.json.JSONObject;
import org.laxio.piston.piston.session.Profile;
import org.laxio.piston.piston.exception.PistonRuntimeException;
import org.laxio.piston.piston.exception.protocol.auth.SessionAuthenticationException;
import org.laxio.piston.piston.session.MinecraftSessionService;
import org.laxio.piston.piston.session.SessionResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class MojangSessionService implements MinecraftSessionService {

    private static final String BASE_URL = "https://sessionserver.mojang.com/session/minecraft/";

    private static final URL CHECK_URL;

    @Override
    public SessionResponse hasJoined(Profile profile, String serverId) throws SessionAuthenticationException {
        try {
            URL request = build(profile, serverId);
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

            Logger.getGlobal().info("Response Message: " + con.getResponseMessage());
            Logger.getGlobal().info("Response Code: " + con.getResponseCode());
            Logger.getGlobal().info(content);

            JSONObject json = new JSONObject(content);
        } catch (Exception ex) {
            throw new SessionAuthenticationException("Unable to check join", ex);
        }

        return null;
    }

    private URL build(Profile profile, String serverId) throws MalformedURLException {
        return new URL(CHECK_URL.toString()
                + "?username=" + profile.getName()
                + "&serverId=" + serverId
                + "&ip=" + "localhost");
    }

    static {
        try {
            CHECK_URL = new URL(BASE_URL + "hasJoined");
        } catch (Exception ex) {
            throw new PistonRuntimeException(new SessionAuthenticationException("Unable to build CHECK_URL", ex));
        }
    }

}
