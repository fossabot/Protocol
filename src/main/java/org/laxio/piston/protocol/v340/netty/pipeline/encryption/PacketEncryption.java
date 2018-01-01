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
package org.laxio.piston.protocol.v340.netty.pipeline.encryption;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import javax.crypto.ShortBufferException;
import java.security.Key;
import java.util.Random;

public class PacketEncryption {

    private byte[] verifyToken = new byte[4];

    private InCipher inCipher;
    private OutCipher outCipher;

    public PacketEncryption() {
        new Random().nextBytes(verifyToken);
    }

    public byte[] getVerifyToken() {
        return verifyToken;
    }

    public void generate(Key key) {
        this.inCipher = new InCipher(this, key);
        this.outCipher = new OutCipher(this, key);
    }

    public InCipher getInCipher() {
        return inCipher;
    }

    public OutCipher getOutCipher() {
        return outCipher;
    }

    public void encrypt(ByteBuf input, ByteBuf output) throws ShortBufferException {
        outCipher.encrypt(input, output);
    }

    public ByteBuf decrypt(ChannelHandlerContext context, ByteBuf input) throws ShortBufferException {
        return inCipher.decrypt(context, input);
    }

}
