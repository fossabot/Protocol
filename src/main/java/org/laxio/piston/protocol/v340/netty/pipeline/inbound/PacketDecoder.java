package org.laxio.piston.protocol.v340.netty.pipeline.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
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

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws IOException, DataFormatException {
        if (byteBuf.readableBytes() > 0) {
            Logger.getGlobal().info("Received packet: " + byteBuf.readableBytes() + " bytes");
            PistonByteBuf buffer = new PistonByteBuf(byteBuf);
            PistonInputStream stream = new PistonInputStream(new ByteBufInputStream(buffer.getBuf()));

            int id = stream.readVarInt();
            // list.add()
            // TODO: get packet by id, build packet, etc

            if (id == 0x00) {
                HandshakePacket packet = new HandshakePacket();
                packet.read(stream);
                Logger.getGlobal().info("Received: " + packet);
            }

            Logger.getGlobal().info("Received packet: #" + id);
        }
    }

}
