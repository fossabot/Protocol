package org.laxio.piston.protocol.v340.util;

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

import org.laxio.piston.piston.exception.PistonRuntimeException;
import org.laxio.piston.piston.exception.protocol.auth.SessionAuthenticationException;
import org.laxio.piston.piston.logging.Logger;
import org.laxio.piston.piston.session.MinecraftSessionService;
import org.laxio.piston.piston.session.Profile;
import org.laxio.piston.piston.session.SessionResponse;

import java.util.UUID;

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
