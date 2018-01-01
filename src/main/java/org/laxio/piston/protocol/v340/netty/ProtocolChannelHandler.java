package org.laxio.piston.protocol.v340.netty;

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

import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.laxio.piston.piston.PistonServer;
import org.laxio.piston.piston.protocol.Protocol;
import org.laxio.piston.protocol.v340.netty.pipeline.inbound.PacketDecoder;
import org.laxio.piston.protocol.v340.netty.pipeline.inbound.PacketInflater;
import org.laxio.piston.protocol.v340.netty.pipeline.inbound.PacketSplitter;
import org.laxio.piston.protocol.v340.netty.pipeline.inbound.PacketToNativeTranslator;
import org.laxio.piston.protocol.v340.netty.pipeline.outbound.PacketDeflater;
import org.laxio.piston.protocol.v340.netty.pipeline.outbound.PacketEncoder;
import org.laxio.piston.protocol.v340.netty.pipeline.outbound.PacketPrepender;
import org.laxio.piston.protocol.v340.netty.pipeline.outbound.PacketToClientTranslator;

public class ProtocolChannelHandler extends ChannelInitializer<SocketChannel> {

    private final PistonServer server;
    private final Protocol nativeProtocol;

    ProtocolChannelHandler(PistonServer server) {
        this.server = server;
        this.nativeProtocol = server.getProtocol();
    }

    @Override
    protected void initChannel(SocketChannel channel) {
        try {
            channel.config().setOption(ChannelOption.TCP_NODELAY, true);
        } catch (ChannelException ex) {
            // TODO: check if this can be ignored
        }

        NetworkClient client = new NetworkClient(this.server, this.nativeProtocol);

        /*
         * ========================
         *
         *         INBOUND
         *
         * ========================
         */

        // stage 1: input - timeout
        // disconnect after 30 secs of no io
        channel.pipeline().addLast("timeout", new ReadTimeoutHandler(30));

        // stage 2: input - splitter
        // reads the length of the packet and stores it in a bytebuf
        channel.pipeline().addLast("splitter", new PacketSplitter());

        // stage 3: input - inflater
        // inflates the buffer if compression is enabled
        channel.pipeline().addLast("inflater", new PacketInflater(client));

        // stage 4: input - decoder
        // decodes the data into a readable packet
        channel.pipeline().addLast("decoder", new PacketDecoder(client));

        // stage 5: input - translate
        // translates client packet to native
        channel.pipeline().addLast("native-translator", new PacketToNativeTranslator());

        // stage 6: input - handle
        // handle the native packet
        channel.pipeline().addLast("client", client);

        /*
         * ========================
         *
         *         OUTBOUND
         *
         * ========================
         */

        // stage 4: output - deflater
        // deflater - compresses the packet
        channel.pipeline().addLast("deflater", new PacketDeflater(client));

        // stage 3: output - prepender
        // prepender - prepends packet length onto bytes before sending
        channel.pipeline().addLast("prepender", new PacketPrepender());

        // stage 2: output - encode
        // encodes the data into a client readable
        channel.pipeline().addLast("encoder", new PacketEncoder(client));

        // stage 1: output - translate
        // translates native packet into client
        channel.pipeline().addLast("client-translator", new PacketToClientTranslator());
    }

}
