package org.laxio.piston.protocol.v340.netty.pipeline.inbound;

        /*-
         * #%L
         * Protocol
         * %%
         * Copyright (C) 2017 - 2018 Laxio
         * %%
         * This file is part of Piston, licensed under the MIT License (MIT).
         *
         * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
         *
         * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
         *
         * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
         * #L%
         */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.laxio.piston.protocol.v340.netty.NetworkClient;
import org.laxio.piston.protocol.v340.stream.PistonByteBuf;

import java.io.IOException;
import java.util.List;
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
