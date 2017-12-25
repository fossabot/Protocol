package org.laxio.piston.protocol.v340.netty.pipeline.outbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.laxio.piston.piston.protocol.Packet;

import java.util.List;

public class PacketToClientTranslator extends MessageToMessageEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, List<Object> list) {
        // TODO: translate native packet to client
        list.add(packet);
    }

}
