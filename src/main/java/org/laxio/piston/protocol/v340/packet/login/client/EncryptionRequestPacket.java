package org.laxio.piston.protocol.v340.packet.login.client;

import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;
import java.security.PublicKey;
import java.util.Arrays;

public class EncryptionRequestPacket extends ProtocolPacket {

    private String serverName;
    private PublicKey key;
    private byte[] verifyToken;

    public EncryptionRequestPacket() {
        // required empty packet
    }

    public EncryptionRequestPacket(String serverName, PublicKey key, byte[] verifyToken) {
        this.serverName = serverName;
        this.key = key;
        this.verifyToken = verifyToken;
    }

    public String getServerName() {
        return serverName;
    }

    public PublicKey getKey() {
        return key;
    }

    public byte[] getVerifyToken() {
        return verifyToken;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeString(serverName);
        output.writeBytes(key.getEncoded());
        output.writeBytes(verifyToken);
    }

    @Override
    public String toString() {
        return "EncryptionRequestPacket{" +
                "serverName='" + serverName + '\'' +
                ", key=" + key +
                ", verifyToken=" + Arrays.toString(verifyToken) +
                '}';
    }

}
