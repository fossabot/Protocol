package org.laxio.piston.protocol.v001.netty.pipeline.outbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.laxio.piston.protocol.v001.netty.NetworkClient;
import org.laxio.piston.protocol.v001.stream.PistonByteBuf;

import java.util.logging.Logger;
import java.util.zip.Deflater;

/**
 * Inflates packets when compression is set
 */
public class PacketDeflater extends MessageToByteEncoder<ByteBuf> {

    private final NetworkClient client;

    public PacketDeflater(NetworkClient client) {
        this.client = client;
    }

    /**
     * Compresses the input buffer when compression is set and the packet is larger than the threshold
     *
     * @param channelHandlerContext The channel which this packet is being sent through
     * @param input The input buffer
     * @param output The output buffer (may be compressed)
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf input, ByteBuf output) throws Exception {
        int length = input.readableBytes();
        if (this.client.getCompression().isEnabled() && this.client.getCompression().getThreshold() >= length) {
            Logger.getGlobal().info("Compressing packet: " + input.readableBytes() + " bytes");
            PistonByteBuf out = new PistonByteBuf(output);
            out.writeVarInt(input.readableBytes());

            byte[] in = input.array();
            Deflater deflater = new Deflater();
            deflater.setInput(in);
            deflater.finish();

            byte[] bytes = new byte[length];
            int len = deflater.deflate(bytes);
            deflater.end();

            byte[] trim = new byte[len];
            System.arraycopy(bytes, 0, trim, 0, trim.length);

            output.writeBytes(trim);
            return;
        }

        output.writeBytes(input);
    }

}