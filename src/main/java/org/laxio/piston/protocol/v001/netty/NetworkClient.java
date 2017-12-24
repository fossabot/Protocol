package org.laxio.piston.protocol.v001.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.laxio.piston.piston.protocol.Packet;
import org.laxio.piston.protocol.v001.stream.PistonByteBuf;
import org.laxio.piston.protocol.v001.stream.PistonInputStream;
import org.laxio.piston.protocol.v001.stream.compression.CompressionState;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.zip.Inflater;

/**
 * Channel connection between the server and client, manages Packet conversion to/from bytes
 */
public class NetworkClient extends ChannelInboundHandlerAdapter {

    private boolean preparing = true;
    private ChannelHandlerContext context;
    private Channel channel;
    private SocketAddress address;

    private final CompressionState compression;

    public NetworkClient() {
        this.compression = new CompressionState(-1);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        this.context = ctx;
        this.channel = ctx.channel();
        this.address = this.channel.remoteAddress();
        this.preparing = false;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        PistonByteBuf buffer = new PistonByteBuf(buf);
        int len = buffer.readVarInt(); // length of packet

        PistonInputStream stream = null;
        boolean decompressed = false;
        if (this.compression.isEnabled()) {
            int dlen = buffer.readVarInt(); // length of uncompressed data
            if (dlen >= this.compression.getThreshold()) {
                decompressed = true;

                byte[] input = buffer.getBuf().array();       // Converts buffer to an array of bytes
                Inflater decompresser = new Inflater();       // Creates a new inflater
                decompresser.setInput(input);                 // Sets the input of the inflater to the supplied bytes

                byte[] output = new byte[dlen];               // Byte array the size of the uncompressed data
                int resultLen = decompresser.inflate(output); // Inflates the input into the output
                decompresser.end();                           // Closes the decompresser

                // Trim any extra bytes off the end
                byte[] trim = new byte[resultLen];
                System.arraycopy(output, 0, trim, 0, trim.length);

                stream = new PistonInputStream(new ByteArrayInputStream(trim));
            }
        }

        if (!decompressed) {
            stream = new PistonInputStream(new ByteBufInputStream(buffer.getBuf()));
        }

        int id = stream.readVarInt();
        // TODO: get packet by id, build packet, etc
    }

    public void sendPacket(Packet packet) throws IOException {

    }

}
