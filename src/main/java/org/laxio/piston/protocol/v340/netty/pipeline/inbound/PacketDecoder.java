package org.laxio.piston.protocol.v340.netty.pipeline.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.laxio.piston.piston.protocol.Packet;
import org.laxio.piston.piston.protocol.PacketDirection;
import org.laxio.piston.piston.util.Environment;
import org.laxio.piston.protocol.v340.netty.NetworkClient;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;
import org.laxio.piston.protocol.v340.stream.PistonByteBuf;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Decodes the buffer into a readable packet
 */
public class PacketDecoder extends ByteToMessageDecoder {

    private final NetworkClient client;

    public PacketDecoder(NetworkClient client) {
        this.client = client;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() > 0) {
            PistonByteBuf buffer = new PistonByteBuf(byteBuf);
            // PistonInputStream stream = new PistonInputStream(new ByteBufInputStream(buffer.getBuf()));

            final int id = buffer.readVarInt();
            if (Environment.isDebugMode()) {
                final ByteBuf clone = Unpooled.copiedBuffer(byteBuf);

                new Thread(() -> {
                    try {
                        byte[] data = new byte[clone.readableBytes()];
                        for (int i = 0; i < data.length; i++) {
                            data[i] = clone.readByte();
                        }

                        Path file = Paths.get("packets/" + client.getState().name() + "-SERVERBOUND-" + id + ".packet");
                        Files.write(file, data);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }

            Packet packet = client.getProtocol().getPacket(client.getState(), PacketDirection.SERVERBOUND, id);
            if (packet instanceof ProtocolPacket) {
                ProtocolPacket pkt = (ProtocolPacket) packet;
                pkt.version = client.getProtocolVersion();
            }

            packet.setServer(client.getServer());
            packet.setConnection(client);
            packet.read(buffer);

            if (Environment.isDebugMode()) {
                client.getServer().getLogger().config("Received Packet #{}: {}", id, packet.toString());
            }

            list.add(packet);
        }
    }

}
