package org.laxio.piston.protocol.v001.netty.pipeline.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.laxio.piston.piston.protocol.Packet;

import java.util.List;

public class PacketToNativeTranslator extends MessageToMessageDecoder<Packet> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Packet packet, List<Object> list) {
        // TODO: translate client packet to native
        list.add(packet);
    }

}
