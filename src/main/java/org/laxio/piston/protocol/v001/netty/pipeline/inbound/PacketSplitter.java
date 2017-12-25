package org.laxio.piston.protocol.v001.netty.pipeline.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import org.laxio.piston.protocol.v001.stream.PistonInputStream;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Splits the buffer into smaller buffers for each Packet
 * Trims the packet length from the buffer
 */
public class PacketSplitter extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byteBuf.markReaderIndex();
        byte[] abyte = new byte[3];

        for (int i = 0; i < abyte.length; ++i) {
            if (!byteBuf.isReadable()) {
                byteBuf.resetReaderIndex();
                return;
            }

            abyte[i] = byteBuf.readByte();
            if (abyte[i] >= 0) {

                try (PistonInputStream stream = new PistonInputStream(new ByteArrayInputStream(abyte))) {
                    int j = stream.readVarInt();

                    if (byteBuf.readableBytes() >= j) {
                        list.add(byteBuf.readBytes(j));
                        return;
                    }

                    byteBuf.resetReaderIndex();
                }

                return;
            }
        }

        throw new CorruptedFrameException("length wider than 21-bit");

    }

}
