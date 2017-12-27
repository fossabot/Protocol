package org.laxio.piston.protocol.v340.netty.pipeline.encryption;

import io.netty.buffer.ByteBuf;
import org.laxio.piston.piston.exception.protocol.ProtocolEncryptionException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.GeneralSecurityException;
import java.security.Key;

public abstract class CipherStore {

    private final PacketEncryption parent;

    protected byte[] before = new byte[0];
    protected byte[] after = new byte[0];

    protected final Cipher cipher;

    CipherStore(PacketEncryption parent, Key key) {
        this.parent = parent;
        this.cipher = generate(getOpMode(), key);
    }

    public byte[] getVerifyToken() {
        return parent.getVerifyToken();
    }

    protected abstract int getOpMode();

    private Cipher generate(int opmode, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(opmode, key, new IvParameterSpec(key.getEncoded()));
            return cipher;
        } catch (GeneralSecurityException generalsecurityexception) {
            throw new ProtocolEncryptionException(generalsecurityexception);
        }
    }

    protected byte[] store(ByteBuf bytebuf) {
        int i = bytebuf.readableBytes();

        if (before.length < i) {
            before = new byte[i];
        }

        bytebuf.readBytes(before, 0, i);
        return before;
    }

}
