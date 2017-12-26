package org.laxio.piston.protocol.v340.netty.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;

public class PacketEncryption {

    private byte[] before = new byte[0];
    private byte[] after = new byte[0];

    private Cipher cipher;

    public Cipher generate(int opmode, Key key) {
        try {
            cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(opmode, key, new IvParameterSpec(key.getEncoded()));
            return cipher;
        } catch (GeneralSecurityException generalsecurityexception) {
            throw new RuntimeException(generalsecurityexception);
        }
    }

    public Cipher getCipher() {
        return cipher;
    }

    private byte[] store(ByteBuf bytebuf) {
        int i = bytebuf.readableBytes();

        if (before.length < i) {
            before = new byte[i];
        }

        bytebuf.readBytes(before, 0, i);
        return before;
    }

    public void encrypt(ByteBuf input, ByteBuf output) throws ShortBufferException {
        int i = input.readableBytes();
        byte[] abyte = store(input);
        int j = cipher.getOutputSize(i);

        if (after.length < j) {
            after = new byte[j];
        }

        output.writeBytes(after, 0, cipher.update(abyte, 0, i, after));
    }

    public ByteBuf decrypt(ChannelHandlerContext context, ByteBuf input) throws ShortBufferException {
        int i = input.readableBytes();
        byte[] abyte = store(input);
        ByteBuf bytebuf1 = context.alloc().heapBuffer(cipher.getOutputSize(i));

        bytebuf1.writerIndex(cipher.update(abyte, 0, i, bytebuf1.array(), bytebuf1.arrayOffset()));
        return bytebuf1;
    }

}