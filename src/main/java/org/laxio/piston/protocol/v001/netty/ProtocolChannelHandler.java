package org.laxio.piston.protocol.v001.netty;

import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.laxio.piston.protocol.v001.netty.pipeline.*;

public class ProtocolChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        try {
            channel.config().setOption(ChannelOption.TCP_NODELAY, true);
        } catch (ChannelException ex) {
            // TODO: check if this can be ignored
        }


        NetworkClient client = new NetworkClient();

        // stage 1: input - timeout
        // disconnect after 30 secs of no io
        channel.pipeline().addLast(new ReadTimeoutHandler(30));

        // stage 2: input - splitter
        // reads the length of the packet and stores it in a bytebuf
        channel.pipeline().addLast(new PacketSplitter());

        // stage 3: input - inflater
        // inflates the buffer if compression is enabled
        channel.pipeline().addLast(new PacketInflater(client));

        // stage 4: input - decoder
        // decodes the data into a readable packet
        channel.pipeline().addLast(new PacketDecoder());

        // stage 3: output - prepender
        // prepender - prepends packet length onto bytes before sending
        channel.pipeline().addLast(new PacketPrepender());

        // stage 2: output - deflater
        // deflater - compresses the
        channel.pipeline().addLast(new PacketDeflater(client));
    }

}
