package org.laxio.piston.protocol.v340.netty.pipeline.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.laxio.piston.piston.protocol.Packet;
import org.laxio.piston.piston.protocol.ProtocolState;
import org.laxio.piston.piston.protocol.UnsupportedPacket;
import org.laxio.piston.piston.translator.ProtocolTranslator;
import org.laxio.piston.protocol.v340.netty.NetworkClient;

import java.util.List;

public class PacketToNativeTranslator extends MessageToMessageDecoder<Packet> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Packet packet, List<Object> list) {
        NetworkClient client = (NetworkClient) packet.getConnection();
        if (packet.getConnection().getState() != ProtocolState.PLAY) {
            list.add(packet);
            return;
        }

        if (packet.getVersion() != client.getServer().getProtocol().getVersion()) {
            Packet translated = packet;
            while (translated.getVersion() != client.getServer().getProtocol().getVersion()) {
                ProtocolTranslator translator = null;
                for (ProtocolTranslator available : client.getServer().getTranslators()) {
                    if (available.getTranslatedVersion() == packet.getVersion()) {
                        translator = available;
                        break;
                    }
                }

                if (translator == null) {
                    list.add(new UnsupportedPacket(translated));
                    return;
                }

                translated = translator.translateToNative(packet);
            }

            list.add(translated);
            return;
        }

        list.add(packet);
    }

}
