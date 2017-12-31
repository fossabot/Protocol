package org.laxio.piston.protocol.v340.packet.status.server;

import org.junit.jupiter.api.Test;
import org.laxio.piston.protocol.v340.TestConnection;
import org.laxio.piston.protocol.v340.TestServer;
import org.laxio.piston.protocol.v340.stream.PistonInputStream;

import java.io.IOException;
import java.io.InputStream;

class RequestPacketTest {

    @Test
    void read() throws IOException {
        TestServer server = new TestServer();
        TestConnection connection = new TestConnection();

        InputStream stream = getClass().getResourceAsStream("/status/request.packet");
        PistonInputStream input = new PistonInputStream(stream);
        RequestPacket packet = new RequestPacket();

        packet.setServer(server);
        packet.setConnection(connection);
        packet.read(input);

        assert (packet.isLocked());
    }

}
