package org.laxio.piston.protocol.v340.packet.login.server;

import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;
import java.security.PublicKey;

public class EncyrptionResponsePacket extends ProtocolPacket {

    private byte[] sharedSecret;
    private byte[] verifyToken;

    public byte[] getSharedSecret() {
        return sharedSecret;
    }

    public byte[] getVerifyToken() {
        return verifyToken;
    }

    @Override
    public void onRead(PistonInput input) throws IOException {
        sharedSecret = input.readBytes();
        verifyToken = input.readBytes();
    }

}
