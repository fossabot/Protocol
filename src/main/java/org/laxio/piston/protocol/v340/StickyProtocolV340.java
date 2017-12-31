package org.laxio.piston.protocol.v340;

import org.laxio.piston.piston.PistonServer;
import org.laxio.piston.piston.exception.PistonRuntimeException;
import org.laxio.piston.piston.exception.protocol.packet.PacketNotFoundException;
import org.laxio.piston.piston.exception.protocol.packet.UnsupportedPacketException;
import org.laxio.piston.piston.protocol.Packet;
import org.laxio.piston.piston.protocol.PacketDirection;
import org.laxio.piston.piston.protocol.Protocol;
import org.laxio.piston.piston.protocol.ProtocolState;
import org.laxio.piston.piston.util.map.ReverseMap;
import org.laxio.piston.protocol.v340.packet.handshake.client.LegacyServerListResponsePacket;
import org.laxio.piston.protocol.v340.packet.handshake.server.HandshakePacket;
import org.laxio.piston.protocol.v340.packet.handshake.server.LegacyServerListPingPacket;
import org.laxio.piston.protocol.v340.packet.login.client.DisconnectPacket;
import org.laxio.piston.protocol.v340.packet.login.client.EncryptionRequestPacket;
import org.laxio.piston.protocol.v340.packet.login.client.LoginSuccessPacket;
import org.laxio.piston.protocol.v340.packet.login.client.SetCompressionPacket;
import org.laxio.piston.protocol.v340.packet.login.server.EncryptionResponsePacket;
import org.laxio.piston.protocol.v340.packet.login.server.LoginStartPacket;
import org.laxio.piston.protocol.v340.packet.play.client.*;
import org.laxio.piston.protocol.v340.packet.play.server.TeleportConfirmPacket;
import org.laxio.piston.protocol.v340.packet.status.client.PongPacket;
import org.laxio.piston.protocol.v340.packet.status.client.ResponsePacket;
import org.laxio.piston.protocol.v340.packet.status.server.PingPacket;
import org.laxio.piston.protocol.v340.packet.status.server.RequestPacket;
import org.laxio.piston.protocol.v340.util.ProtocolMap;

import java.util.Map;

/**
 * Protocol implementation for MC Java Edition protocol 340
 */
public class StickyProtocolV340 implements Protocol {

    public static final int PROTOCOL_VERSION = 340;

    private final ProtocolMap packets;

    private PistonServer server;

    public StickyProtocolV340() {
        this.packets = new ProtocolMap();
    }

    @Override
    public PistonServer getServer() {
        return server;
    }

    @Override
    public void setServer(PistonServer server) {
        if (this.server != null) {
            throw new UnsupportedOperationException("Server already set");
        }

        this.server = server;
        init();
    }

    /**
     * Finds and constructs a packet based on the supplied information
     *
     * @param state     The state that the protocol is in
     * @param direction The direction which the packet is travelling
     * @param id        The id number of the packet
     *
     * @return The constructed Packet
     *
     * @throws PacketNotFoundException    If no Packet is found matching the criteria
     * @throws UnsupportedPacketException If the Packet cannot be constructed via reflection
     */
    @Override
    public Packet getPacket(ProtocolState state, PacketDirection direction, int id) throws UnsupportedPacketException, PacketNotFoundException {
        return this.packets.construct(state, direction, id);
    }

    @Override
    public int getId(Packet packet) throws PacketNotFoundException {
        return packets.getId(packet);
    }

    @Override
    public int getVersion() {
        return PROTOCOL_VERSION;
    }

    private void init() {
        try {
            // HANDSHAKE
            this.packets.add(ProtocolState.HANDSHAKE, PacketDirection.SERVERBOUND, HandshakePacket.class);
            this.packets.add(ProtocolState.HANDSHAKE, PacketDirection.SERVERBOUND, 0xFE, LegacyServerListPingPacket.class);

            this.packets.add(ProtocolState.HANDSHAKE, PacketDirection.CLIENTBOUND, 0xFF, LegacyServerListResponsePacket.class);

            // PLAY
            this.packets.add(ProtocolState.PLAY, PacketDirection.SERVERBOUND, TeleportConfirmPacket.class);

            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, SpawnObjectPacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, SpawnExperienceOrbPacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, SpawnGlobalEntityPacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, SpawnMobPacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, SpawnPaintingPacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, SpawnPlayerPacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, ServerAnimationPacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, StatisticsPacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, BlockBreakAnimationPacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, UpdateBlockEntityPacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, BlockActionPacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, BlockChangePacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, BossBarPacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, ServerDifficultyPacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, ServerTabCompletePacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, ServerChatMessagePacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, MultiBlockChangePacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, ServerConfirmTransactionPacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, ServerCloseWindowPacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, OpenWindowPacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, WindowItemsPacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, WindowPropertyPacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, SetSlotPacket.class);
            this.packets.add(ProtocolState.PLAY, PacketDirection.CLIENTBOUND, ServerPluginMessagePacket.class);

            // STATUS
            this.packets.add(ProtocolState.STATUS, PacketDirection.SERVERBOUND, RequestPacket.class);
            this.packets.add(ProtocolState.STATUS, PacketDirection.SERVERBOUND, PingPacket.class);

            this.packets.add(ProtocolState.STATUS, PacketDirection.CLIENTBOUND, ResponsePacket.class);
            this.packets.add(ProtocolState.STATUS, PacketDirection.CLIENTBOUND, PongPacket.class);

            // LOGIN
            this.packets.add(ProtocolState.LOGIN, PacketDirection.SERVERBOUND, LoginStartPacket.class);
            this.packets.add(ProtocolState.LOGIN, PacketDirection.SERVERBOUND, EncryptionResponsePacket.class);
            this.packets.add(ProtocolState.LOGIN, PacketDirection.SERVERBOUND, LoginSuccessPacket.class);
            this.packets.add(ProtocolState.LOGIN, PacketDirection.SERVERBOUND, SetCompressionPacket.class);

            this.packets.add(ProtocolState.LOGIN, PacketDirection.CLIENTBOUND, DisconnectPacket.class);
            this.packets.add(ProtocolState.LOGIN, PacketDirection.CLIENTBOUND, EncryptionRequestPacket.class);
        } catch (UnsupportedPacketException ex) {
            ex.printStackTrace();
            throw new PistonRuntimeException(ex);
        }

        Map<ProtocolState, Map<PacketDirection, ReverseMap<Integer, Class<? extends Packet>>>> packets = this.packets.getPackets();
        for (ProtocolState state : ProtocolState.values()) {
            Map<PacketDirection, ReverseMap<Integer, Class<? extends Packet>>> map1 = packets.get(state);
            int server = map1.get(PacketDirection.SERVERBOUND).size();
            int client = map1.get(PacketDirection.CLIENTBOUND).size();
            int total = server + client;

            this.server.getLogger().debug("Loaded {} packets for {}. SERVER[{}] CLIENT[{}]", total, state.name(), server, client);
        }
    }

}
