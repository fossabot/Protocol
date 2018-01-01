package org.laxio.piston.protocol.v340.netty.pipeline.outbound;

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
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.laxio.piston.protocol.v340.stream.PistonOutputStream;

public class PacketPrepender extends MessageToByteEncoder<ByteBuf> {

    /**
     * This method checks if the length of the packet can be stored into a VarInt (3 bytes)
     *
     * @param channelHandlerContext The channel to send the packet to
     * @param input                 The input buffer
     * @param output                The output buffer
     *
     * @throws Exception When the length of the data is not a valid var int
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf input, ByteBuf output) throws Exception {
        int bytes = input.readableBytes();
        int length = getVarIntLength(bytes);

        if (length > 3) {
            throw new UnsupportedOperationException("Unable to fit " + bytes + " into 3 bytes");
        } else {
            PistonOutputStream stream = new PistonOutputStream(new ByteBufOutputStream(output));
            output.ensureWritable(length + bytes);
            stream.writeVarInt(bytes);
            output.writeBytes(input, input.readerIndex(), bytes);
        }
    }

    /**
     * Gets the length of the supplied int as a VarInt
     *
     * @param var The int to convert and check
     *
     * @return A byte length between 1 and 5
     */
    private static int getVarIntLength(int var) {
        for (int j = 1; j < 5; ++j) {
            if ((var & -1 << j * 7) == 0) {
                return j;
            }
        }

        return 5;
    }

}
