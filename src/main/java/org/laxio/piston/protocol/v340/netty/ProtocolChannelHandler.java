package org.laxio.piston.protocol.v340.netty;

import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.laxio.piston.piston.PistonServer;
import org.laxio.piston.protocol.v340.StickyProtocolV340;
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
    private final StickyProtocolV340 nativeProtocol;

    ProtocolChannelHandler(PistonServer server) {
        this.server = server;
        this.nativeProtocol = new StickyProtocolV340();
    }

    @Override
    protected void initChannel(SocketChannel channel) {
        try {
            channel.config().setOption(ChannelOption.TCP_NODELAY, true);
        } catch (ChannelException ex) {
            // TODO: check if this can be ignored
        }

        // TODO: encryption
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
        channel.pipeline().addLast(new ReadTimeoutHandler(30));

        // stage 2: input - decrypt
        // decrypts the buffer
        // channel.pipeline().addLast(new PacketDecrypter());

        // stage 3: input - splitter
        // reads the length of the packet and stores it in a bytebuf
        channel.pipeline().addLast(new PacketSplitter());

        // stage 4: input - inflater
        // inflates the buffer if compression is enabled
        channel.pipeline().addLast(new PacketInflater(client));

        // stage 5: input - decoder
        // decodes the data into a readable packet
        channel.pipeline().addLast(new PacketDecoder(client));

        // stage 6: input - translate
        // translates client packet to native
        channel.pipeline().addLast(new PacketToNativeTranslator());

        // stage 7: input - handle
        // handle the native packet
        channel.pipeline().addLast(client);

        /*
         * ========================
         *
         *         OUTBOUND
         *
         * ========================
         */

        // stage 5: output - encrypt
        // encrypt - encrypt the packet
        // channel.pipeline().addLast(new PacketEncrypter(client));

        // stage 4: output - deflater
        // deflater - compresses the packet
        channel.pipeline().addLast(new PacketDeflater(client));

        // stage 3: output - prepender
        // prepender - prepends packet length onto bytes before sending
        channel.pipeline().addLast(new PacketPrepender());

        // stage 2: output - encode
        // encodes the data into a client readable
        channel.pipeline().addLast(new PacketEncoder(client));

        // stage 1: output - translate
        // translates native packet into client
        channel.pipeline().addLast(new PacketToClientTranslator());
    }

}
