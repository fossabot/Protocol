/*-
 * ========================LICENSE_START========================
 * Protocol
 * %%
 * Copyright (C) 2017 - 2018 Laxio
 * %%
 * This file is part of Piston, licensed under the MIT License (MIT).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * ========================LICENSE_END========================
 */
package org.laxio.piston.protocol.v340.packet;

import org.laxio.piston.piston.PistonServer;
import org.laxio.piston.piston.exception.protocol.packet.PacketConfigurationException;
import org.laxio.piston.piston.protocol.Connection;
import org.laxio.piston.piston.protocol.Packet;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.StickyProtocolV340;

import java.io.IOException;

/**
 * Basic Packet extended by detailed packets
 * Includes a locking system to prevent packets from being read multiple times
 */
public abstract class ProtocolPacket implements Packet {

    public int version = StickyProtocolV340.PROTOCOL_VERSION;
    private boolean locked;
    private Connection connection;
    private PistonServer server;

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        if (this.connection != null)
            throw new PacketConfigurationException("Connection already set", this);

        this.connection = connection;
    }

    @Override
    public PistonServer getServer() {
        return server;
    }

    @Override
    public void setServer(PistonServer server) {
        if (this.server != null)
            throw new PacketConfigurationException("Server already set", this);

        this.server = server;
    }

    @Override
    public void read(PistonInput input) throws IOException {
        if (this.server == null)
            throw new PacketConfigurationException("Server has not been set", this);

        if (this.connection == null)
            throw new PacketConfigurationException("Connection has not been set", this);

        checkLock();
        onRead(input);
        lock();
    }

    @Override
    public void onRead(PistonInput input) throws IOException {
        throw new UnsupportedOperationException("This Packet cannot be read by the server");
    }

    @Override
    public void write(PistonOutput output) throws IOException {
        if (this.server == null)
            throw new PacketConfigurationException("Server has not been set", this);

        if (this.connection == null)
            throw new PacketConfigurationException("Connection has not been set", this);

        onWrite(output);
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        throw new UnsupportedOperationException("This Packet cannot be written by the server");
    }

    /**
     * Locks the Packet so new data cannot be read
     */
    private void lock() {
        this.locked = true;
    }

    /**
     * Checks the lock status
     *
     * @throws PacketConfigurationException If the Packet is locked
     */
    private void checkLock() {
        if (this.locked) {
            throw new PacketConfigurationException("Packet has been locked and cannot be read to", this);
        }
    }

    /**
     * Used in tests
     *
     * @return Returns if this packet is locked
     */
    public boolean isLocked() {
        return this.locked;
    }

    public abstract String toString();

}
