package org.laxio.piston.protocol.v340.netty.pipeline.inbound;

        /*-
         * #%L
         * Protocol
         * %%
         * Copyright (C) 2017 - 2018 Laxio
         * %%
         * This file is part of Piston, licensed under the MIT License (MIT).
         *
         * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
         *
         * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
         *
         * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
         * #L%
         */

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
