package org.laxio.piston.protocol.v340.netty.pipeline.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.laxio.piston.piston.protocol.Packet;
import org.laxio.piston.piston.protocol.PacketDirection;
import org.laxio.piston.protocol.v340.netty.NetworkClient;
import org.laxio.piston.protocol.v340.packet.handshake.server.HandshakePacket;
import org.laxio.piston.protocol.v340.stream.PistonByteBuf;
import org.laxio.piston.protocol.v340.stream.PistonInputStream;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;

/**
 * Decodes the buffer into a readable packet
 */
public class PacketDecoder extends ByteToMessageDecoder {

    private final NetworkClient client;

    public PacketDecoder(NetworkClient client) {
        this.client = client;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() > 0) {
            PistonByteBuf buffer = new PistonByteBuf(byteBuf);
            // PistonInputStream stream = new PistonInputStream(new ByteBufInputStream(buffer.getBuf()));

            int id = buffer.readVarInt();
            Logger.getGlobal().info("Read id #" + id);

            Packet packet = client.getProtocol().getPacket(client.getState(), PacketDirection.SERVERBOUND, id);
            packet.setServer(client.getServer());
            packet.setConnection(client);
            packet.read(buffer);

            list.add(packet);

            Logger.getGlobal().info("Received packet: #" + id + " - " + packet);
        }
    }

}
