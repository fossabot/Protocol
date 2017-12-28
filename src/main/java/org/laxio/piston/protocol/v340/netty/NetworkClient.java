package org.laxio.piston.protocol.v340.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.laxio.piston.piston.PistonServer;
import org.laxio.piston.piston.exception.PistonRuntimeException;
import org.laxio.piston.piston.exception.protocol.auth.SessionAuthenticationException;
import org.laxio.piston.piston.protocol.Connection;
import org.laxio.piston.piston.protocol.Packet;
import org.laxio.piston.piston.protocol.Protocol;
import org.laxio.piston.piston.protocol.ProtocolState;
import org.laxio.piston.protocol.v340.netty.pipeline.ChannelInboundMessageAdapter;
import org.laxio.piston.protocol.v340.netty.pipeline.encryption.PacketEncryption;
import org.laxio.piston.protocol.v340.netty.pipeline.inbound.PacketDecrypter;
import org.laxio.piston.protocol.v340.netty.pipeline.outbound.PacketEncrypter;
import org.laxio.piston.protocol.v340.packet.handshake.server.HandshakePacket;
import org.laxio.piston.protocol.v340.stream.compression.CompressionState;
import org.laxio.piston.protocol.v340.util.UserProfile;

import java.net.SocketAddress;
import java.util.logging.Logger;

/**
 * Channel connection between the server and client, manages Packet conversion to/from bytes
 */
public class NetworkClient extends ChannelInboundMessageAdapter<Packet> implements Connection {

    private final CompressionState compression;
    private boolean preparing = true;
    private ChannelHandlerContext context;
    private Channel channel;
    private SocketAddress address;
    private ProtocolState state = ProtocolState.HANDSHAKE;

    private PistonServer server;
    private Protocol protocol;

    private UserProfile profile;
    private boolean encrypted = false;
    private PacketEncryption encryption;
    private PacketEncryption encryptionHold;

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

    @Override
    public ProtocolState getState() {
        return state;
    }

    @Override
    public void setState(ProtocolState state) {
        this.state = state;
    }

    public PistonServer getServer() {
        return server;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        if (this.profile != null) {
            throw new PistonRuntimeException(new SessionAuthenticationException("Profile already set for this connection"));
        }

        this.profile = profile;
    }

    @Override
    public boolean isEncrypted() {
        return encrypted;
    }

    public PacketEncryption getEncryption() {
        return encryption;
    }

    public void setEncryption(PacketEncryption encryption) {
        this.encryption = encryption;

        Logger.getGlobal().info("Set encryption");
        this.channel.pipeline().addBefore("splitter", "decrypt", new PacketDecrypter(encryption));
        this.channel.pipeline().addBefore("prepender", "encrypt", new PacketEncrypter(encryption));

        this.encrypted = true;
    }

    public PacketEncryption getEncryptionHold() {
        return encryptionHold;
    }

    public void setEncryptionHold(PacketEncryption encryptionHold) {
        this.encryptionHold = encryptionHold;
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

        server.getManager().call(msg);
    }

    @Override
    public void sendPacket(Packet packet) {
        packet.setServer(server);
        packet.setConnection(this);
        server.getManager().call(packet);
        if (channel.eventLoop().inEventLoop()) {
            channel.writeAndFlush(packet);
        } else {
            channel.eventLoop().execute(() -> channel.writeAndFlush(packet));
        }
    }

}
