package org.laxio.piston.protocol.v340.netty.pipeline.outbound;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.laxio.piston.piston.exception.protocol.packet.PacketNotFoundException;
import org.laxio.piston.piston.protocol.Packet;
import org.laxio.piston.protocol.v340.netty.NetworkClient;
import org.laxio.piston.protocol.v340.stream.PistonOutputStream;

import java.io.IOException;
import java.util.logging.Logger;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    private final NetworkClient client;

    public PacketEncoder(NetworkClient client) {
        this.client = client;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf buffer) throws IOException {
        int id;
        try {
            id = client.getProtocol().getId(packet);
        } catch (PacketNotFoundException ex) {
            throw new IOException("Can't serialize unregistered packet", ex);
        }

        Logger.getGlobal().info("Attempting to send " + packet);
        PistonOutputStream stream = new PistonOutputStream(new ByteBufOutputStream(buffer));
        stream.writeVarInt(id);
        packet.write(stream);
        Logger.getGlobal().info("Sent " + packet.getClass().getSimpleName());
    }

}
