package org.laxio.piston.protocol.v340.packet.status.server;

import org.junit.jupiter.api.Test;
import org.laxio.piston.protocol.v340.TestConnection;
import org.laxio.piston.protocol.v340.TestServer;
import org.laxio.piston.protocol.v340.stream.PistonInputStream;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PingPacketTest {

    @Test
    void read() throws IOException {
        TestServer server = new TestServer();
        TestConnection connection = new TestConnection();

        InputStream stream = getClass().getResourceAsStream("/status/ping.packet");
        PistonInputStream input = new PistonInputStream(stream);
        PingPacket packet = new PingPacket();

        packet.setServer(server);
        packet.setConnection(connection);
        packet.read(input);

        assertTrue(packet.getPayload() == 62910955);
        assertTrue(packet.isLocked());
    }

}
