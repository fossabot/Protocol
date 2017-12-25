package org.laxio.piston.protocol.v340.netty.pipeline.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.laxio.piston.protocol.v340.netty.NetworkClient;
import org.laxio.piston.protocol.v340.stream.PistonByteBuf;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * Inflates packets when compression is set
 */
public class PacketInflater extends ByteToMessageDecoder {

    private final NetworkClient client;

    public PacketInflater(NetworkClient client) {
        this.client = client;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws IOException, DataFormatException {
        if (byteBuf.readableBytes() > 0) {
            PistonByteBuf buffer = new PistonByteBuf(byteBuf);

            if (this.client.getCompression().isEnabled()) {
                Logger.getGlobal().info("De-compressing packet: " + byteBuf.readableBytes() + " bytes");
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
                    return;
                }
            }

            list.add(byteBuf.readBytes(byteBuf.readableBytes()));
        }
    }

}
