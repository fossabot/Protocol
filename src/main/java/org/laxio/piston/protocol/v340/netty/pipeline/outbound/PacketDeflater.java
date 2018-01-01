/*-
 * ========================LICENSE_START========================
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
 * ========================LICENSE_END========================
 */
package org.laxio.piston.protocol.v340.netty.pipeline.outbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.laxio.piston.protocol.v340.netty.NetworkClient;
import org.laxio.piston.protocol.v340.stream.PistonByteBuf;

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
     * @param input                 The input buffer
     * @param output                The output buffer (may be compressed)
     *
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf input, ByteBuf output) throws Exception {
        int length = input.readableBytes();
        if (this.client.getCompression().isEnabled() && this.client.getCompression().getThreshold() >= length) {
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
