package org.laxio.piston.protocol.v340.packet.handshake.server;

import org.junit.jupiter.api.Test;
import org.laxio.piston.piston.protocol.ProtocolState;
import org.laxio.piston.protocol.v340.TestConnection;
import org.laxio.piston.protocol.v340.TestServer;
import org.laxio.piston.protocol.v340.stream.PistonInputStream;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HandshakePacketTest {

    @Test
    void read() throws IOException {
        TestServer server = new TestServer();
        TestConnection connection = new TestConnection();

        InputStream stream = getClass().getResourceAsStream("/handshake/handshake.packet");
        PistonInputStream input = new PistonInputStream(stream);
        HandshakePacket packet = new HandshakePacket();

        packet.setServer(server);
        packet.setConnection(connection);
        packet.read(input);

        assertTrue(packet.isLocked());
        assertTrue(packet.getAddress().equals("localhost"));
        assertTrue(packet.getPort() == 25565);
        assertTrue(packet.getNextState() == ProtocolState.STATUS);
        assertTrue(packet.getProtocolVersion() == 340);
    }

}