package org.laxio.piston.protocol.v001.netty.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import org.laxio.piston.protocol.v001.netty.NetworkClient;
import org.laxio.piston.protocol.v001.stream.PistonByteBuf;

import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

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
            // compress

        }

        /*
        if (byteBuf.readableBytes() > 0) {
            PistonByteBuf buffer = new PistonByteBuf(byteBuf);

            if (this.client.getCompression().isEnabled()) {
                int dlen = buffer.readVarInt(); // length of uncompressed data
                if (dlen >= this.client.getCompression().getThreshold()) {
                    byte[] input = buffer.getBuf().array();   // Converts buffer to an array of bytes
                    Inflater inflater = new Inflater();       // Creates a new inflater
                    inflater.setInput(input);                 // Sets the input of the inflater to the supplied bytes

                    byte[] output = new byte[dlen];           // Byte array the size of the uncompressed data
                    int resultLen = inflater.inflate(output); // Inflates the input into the output
                    inflater.end();                           // Closes the inflater

                    // Trim any extra bytes off the end
                    byte[] trim = new byte[resultLen];
                    System.arraycopy(output, 0, trim, 0, trim.length);


                    list.add(Unpooled.copiedBuffer(trim));
                }
            }

            list.add(byteBuf);
        }
         */
    }

}
