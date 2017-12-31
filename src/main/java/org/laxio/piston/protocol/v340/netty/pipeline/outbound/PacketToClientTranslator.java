package org.laxio.piston.protocol.v340.netty.pipeline.outbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.laxio.piston.piston.protocol.Packet;
import org.laxio.piston.piston.protocol.UnsupportedPacket;
import org.laxio.piston.piston.translator.ProtocolTranslator;
import org.laxio.piston.protocol.v340.netty.NetworkClient;
import org.laxio.piston.protocol.v340.packet.login.client.DisconnectPacket;

import java.util.List;

public class PacketToClientTranslator extends MessageToMessageEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, List<Object> list) {
        NetworkClient client = (NetworkClient) packet.getConnection();
        if (packet instanceof DisconnectPacket) {
            list.add(packet);
            return;
        }

        if (packet.getVersion() != client.getProtocol().getVersion()) {
            Packet translated = packet;
            while (translated.getVersion() != client.getProtocol().getVersion()) {
                ProtocolTranslator translator = null;
                for (ProtocolTranslator available : client.getServer().getTranslators()) {
                    if (available.getNativeVersion() == packet.getVersion()) {
                        translator = available;
                        break;
                    }
                }

                if (translator == null) {
                    list.add(new UnsupportedPacket(translated));
                    return;
                }

                translated = translator.translateFromNative(packet);
            }

            list.add(translated);
            return;
        }

        list.add(packet);
    }

}
