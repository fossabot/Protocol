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
