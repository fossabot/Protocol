package org.laxio.piston.protocol.v001.packet;

import org.laxio.piston.piston.PistonServer;
import org.laxio.piston.piston.exception.protocol.packet.PacketConfigurationException;
import org.laxio.piston.piston.protocol.Packet;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;

import java.io.IOException;

/**
 * Basic Packet extended by detailed packets
 * Includes a locking system to prevent packets from being read multiple times
 */
public abstract class ProtocolPacket implements Packet {

    private boolean locked;
    private PistonServer server;

    @Override
    public PistonServer getServer() {
        return null;
    }

    @Override
    public void setServer(PistonServer server) {
        if (this.server != null)
            throw new PacketConfigurationException("Server already set", this);

        this.server = server;
    }

    @Override
    public void _read(PistonInput input) throws IOException {
        if (this.server == null)
            throw new PacketConfigurationException("Server has not been set", this);

        checkLock();
        read(input);
        lock();
    }

    @Override
    public void _write(PistonOutput output) throws IOException {
        if (this.server == null)
            throw new PacketConfigurationException("Server has not been set", this);

        write(output);
    }

    /**
     * Locks the Packet so new data cannot be read
     */
    private void lock() {
        this.locked = true;
    }

    /**
     * Checks the lock status
     * @throws PacketConfigurationException If the Packet is locked
     */
    private void checkLock() {
        if (this.locked) {
            throw new PacketConfigurationException("Packet has been locked and cannot be read to", this);
        }
    }

}
