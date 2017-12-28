package org.laxio.piston.protocol.v340.util;

import org.laxio.piston.piston.exception.PistonRuntimeException;
import org.laxio.piston.piston.exception.protocol.auth.SessionAuthenticationException;
import org.laxio.piston.piston.session.MinecraftSessionService;
import org.laxio.piston.piston.session.Profile;
import org.laxio.piston.piston.session.SessionResponse;

import java.util.UUID;
import java.util.logging.Logger;

public class UserProfile implements Profile {

    private final MinecraftSessionService service;
    private final String name;
    private UUID uuid = null;

    public UserProfile(MinecraftSessionService service, String name) {
        this.service = service;
        this.name = name;
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    private void setUniqueId(UUID uuid) {
        if (this.uuid != null) {
            throw new PistonRuntimeException(new SessionAuthenticationException("UUID for '" + name + "' already set"));
        }

        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void authenticate(String hash) {
        try {
            SessionResponse response = service.hasJoined(this, hash);
            setUniqueId(response.getUuid());
        } catch (Exception ex) {
            Logger.getGlobal().info(ex.getMessage());
            // ex.printStackTrace();
        }
    }

    @Override
    public boolean isAuthenticated() {
        return uuid != null;
    }

}
