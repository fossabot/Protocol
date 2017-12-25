package org.laxio.piston.protocol.v340;

import org.laxio.piston.piston.exception.protocol.packet.PacketNotFoundException;
import org.laxio.piston.piston.exception.protocol.packet.UnsupportedPacketException;
import org.laxio.piston.piston.protocol.Packet;
import org.laxio.piston.piston.protocol.Protocol;
import org.laxio.piston.piston.protocol.ProtocolState;
import org.laxio.piston.piston.protocol.PacketDirection;
import org.laxio.piston.protocol.v340.packet.handshake.server.HandshakePacket;
import org.laxio.piston.protocol.v340.packet.status.server.RequestPacket;
import org.laxio.piston.protocol.v340.util.ProtocolMap;

/**
 * Protocol implementation for MC Java Edition protocol 340
 */
public class StickyProtocolV340 implements Protocol {

    private static final String MC_PROTOCOL_VERSION; // MC Version (Specification)
    private static final String STICKY_PROTOCOL_VERSION; // Protocol Version (Implementation)

    private final ProtocolMap packets;

    public StickyProtocolV340() {
        this.packets = new ProtocolMap(this);
        init();
    }

    /**
     * Finds and constructs a packet based on the supplied information
     * @param state The state that the protocol is in
     * @param direction The direction which the packet is travelling
     * @param id The id number of the packet
     * @return The constructed Packet
     * @throws PacketNotFoundException If no Packet is found matching the criteria
     * @throws UnsupportedPacketException If the Packet cannot be constructed via reflection
     */
    @Override
    public Packet getPacket(ProtocolState state, PacketDirection direction, int id) throws UnsupportedPacketException, PacketNotFoundException {
        return this.packets.construct(state, direction, id);
    }

    @Override
    public String getVersion() {
        return STICKY_PROTOCOL_VERSION;
    }

    @Override
    public String getMinecraftVersion() {
        return MC_PROTOCOL_VERSION;
    }

    private void init() {
        try {
            // HANDSHAKE
            this.packets.add(ProtocolState.HANDSHAKE, PacketDirection.SERVERBOUND, HandshakePacket.class);

            // PLAY

            // STATUS
            this.packets.add(ProtocolState.STATUS, PacketDirection.SERVERBOUND, RequestPacket.class);

            // LOGIN
        } catch (UnsupportedPacketException ex) {
            ex.printStackTrace();
        }
    }

    static {
        MC_PROTOCOL_VERSION = StickyProtocolV340.class.getPackage().getSpecificationVersion();
        STICKY_PROTOCOL_VERSION = StickyProtocolV340.class.getPackage().getImplementationVersion();
    }

}
