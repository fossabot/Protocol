package org.laxio.piston.protocol.v340.packet.login.client;

import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;
import java.security.PublicKey;

public class EncyrptionRequestPacket extends ProtocolPacket {

    private String serverName;
    private PublicKey key;
    private byte[] verifyToken;

    public EncyrptionRequestPacket() {
        // required empty packet
    }

    public EncyrptionRequestPacket(String serverName, PublicKey key, byte[] verifyToken) {
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

}
