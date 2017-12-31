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

    private boolean locked;

    public int version = StickyProtocolV340.PROTOCOL_VERSION;
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
