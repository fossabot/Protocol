package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;
import java.util.Arrays;

public class ServerPluginMessagePacket extends ProtocolPacket {

    private String channel;
    private byte[] data;

    public ServerPluginMessagePacket() {
        // required empty constructor
    }

    public ServerPluginMessagePacket(String channel, byte[] data) {
        this.channel = channel;
        this.data = data;
    }

    public String getChannel() {
        return channel;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeString(channel);
        output.writeBytes(data);
    }

    @Override
    public String toString() {
        return "ServerPluginMessagePacket{" +
                "channel='" + channel + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }

}
