package org.laxio.piston.protocol.v001.netty.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.laxio.piston.protocol.v001.stream.PistonByteBuf;
import org.laxio.piston.protocol.v001.stream.PistonInputStream;

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
            PistonByteBuf buffer = new PistonByteBuf(byteBuf);
            PistonInputStream stream = new PistonInputStream(new ByteBufInputStream(buffer.getBuf()));

            int id = stream.readVarInt();
            // TODO: get packet by id, build packet, etc

            Logger.getGlobal().info("Received packet: #" + id);
        }
    }

}
