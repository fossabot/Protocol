package org.laxio.piston.protocol.v340.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.laxio.piston.piston.PistonServer;
import org.laxio.piston.piston.protocol.Packet;
import org.laxio.piston.piston.protocol.Protocol;
import org.laxio.piston.protocol.v340.netty.pipeline.ChannelInboundMessageAdapter;
import org.laxio.piston.piston.protocol.ProtocolState;
import org.laxio.piston.protocol.v340.packet.handshake.server.HandshakePacket;
import org.laxio.piston.protocol.v340.packet.status.client.PongPacket;
import org.laxio.piston.protocol.v340.packet.status.client.ResponsePacket;
import org.laxio.piston.protocol.v340.packet.status.server.PingPacket;
import org.laxio.piston.protocol.v340.packet.status.server.RequestPacket;
import org.laxio.piston.protocol.v340.stream.compression.CompressionState;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.logging.Logger;

/**
 * Channel connection between the server and client, manages Packet conversion to/from bytes
 */
public class NetworkClient extends ChannelInboundMessageAdapter<Packet> {

    private boolean preparing = true;
    private ChannelHandlerContext context;
    private Channel channel;
    private SocketAddress address;

    private final CompressionState compression;
    private ProtocolState state = ProtocolState.HANDSHAKE;

    private PistonServer server;
    private Protocol protocol;

    NetworkClient(PistonServer server, Protocol protocol) {
        this.server = server;
        this.compression = new CompressionState(-1);
        this.protocol = protocol;
    }

    public boolean isPreparing() {
        return preparing;
    }

    public ChannelHandlerContext getContext() {
        return context;
    }

    public Channel getChannel() {
        return channel;
    }

    public SocketAddress getAddress() {
        return address;
    }

    public CompressionState getCompression() {
        return compression;
    }

    public ProtocolState getState() {
        return state;
    }

    public PistonServer getServer() {
        return server;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        this.context = ctx;
        this.channel = ctx.channel();
        this.address = this.channel.remoteAddress();
        this.preparing = false;
    }

    @Override
    public void onMessage(ChannelHandlerContext ctx, Packet msg) {
        if (msg instanceof HandshakePacket) {
            HandshakePacket handshake = (HandshakePacket) msg;
            state = handshake.getNextState();

            // TODO: set protocol based on handshake#getProtocolVersion()
            return;
        }

        if (msg instanceof RequestPacket) {
            sendPacket(new ResponsePacket());
            return;
        }

        if (msg instanceof PingPacket) {
            PingPacket ping = (PingPacket) msg;
            sendPacket(new PongPacket(ping.getPayload()));
        }
        // TODO: manage packet
    }

    public void sendPacket(Packet packet) {
        Logger.getGlobal().info("Attempting to send packet: " + packet);

        packet.setServer(server);
        if (channel.eventLoop().inEventLoop()) {
            channel.writeAndFlush(packet);
        } else {
            channel.eventLoop().execute(() -> channel.writeAndFlush(packet));
        }
    }

}
