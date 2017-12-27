package org.laxio.piston.protocol.v340.netty.pipeline.encryption;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.laxio.piston.piston.exception.protocol.ProtocolEncryptionException;
import org.laxio.piston.protocol.v340.netty.pipeline.encryption.CipherStore;
import org.laxio.piston.protocol.v340.netty.pipeline.encryption.InCipher;
import org.laxio.piston.protocol.v340.netty.pipeline.encryption.OutCipher;

import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Random;
import java.util.logging.Logger;

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
