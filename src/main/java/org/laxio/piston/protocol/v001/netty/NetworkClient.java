package org.laxio.piston.protocol.v001.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.laxio.piston.piston.protocol.Packet;
import org.laxio.piston.protocol.v001.netty.pipeline.ChannelInboundMessageAdapter;
import org.laxio.piston.protocol.v001.stream.compression.CompressionState;

import java.io.IOException;
import java.net.SocketAddress;

/**
 * Channel connection between the server and client, manages Packet conversion to/from bytes
 */
public class NetworkClient extends ChannelInboundMessageAdapter<Packet> {

    private boolean preparing = true;
    private ChannelHandlerContext context;
    private Channel channel;
    private SocketAddress address;

    private final CompressionState compression;

    NetworkClient() {
        this.compression = new CompressionState(-1);
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
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        this.context = ctx;
        this.channel = ctx.channel();
        this.address = this.channel.remoteAddress();
        this.preparing = false;
    }

    @Override
    public void onMessage(ChannelHandlerContext ctx, Packet msg) {
        // TODO: manage packet
    }

    public void sendPacket(Packet packet) throws IOException {
        // TODO: send packet
    }

}
