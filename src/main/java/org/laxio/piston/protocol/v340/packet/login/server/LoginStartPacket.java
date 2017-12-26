package org.laxio.piston.protocol.v340.packet.login.server;

import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class LoginStartPacket extends ProtocolPacket {

    private String name;

    public String getName() {
        return name;
    }

    @Override
    public void onRead(PistonInput input) throws IOException {
        this.name = input.readString();
    }

    @Override
    public String toString() {
        return "LoginStartPacket{" +
                "name='" + name + '\'' +
                '}';
    }

}
