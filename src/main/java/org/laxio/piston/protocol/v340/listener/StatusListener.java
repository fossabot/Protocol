package org.laxio.piston.protocol.v340.listener;

import io.netty.channel.ChannelFutureListener;
import org.laxio.piston.piston.event.PacketHandler;
import org.laxio.piston.piston.event.listener.Listener;
import org.laxio.piston.piston.event.listener.ListenerPriority;
import org.laxio.piston.protocol.v340.netty.NetworkClient;
import org.laxio.piston.protocol.v340.packet.status.client.PongPacket;
import org.laxio.piston.protocol.v340.packet.status.client.ResponsePacket;
import org.laxio.piston.protocol.v340.packet.status.server.PingPacket;
import org.laxio.piston.protocol.v340.packet.status.server.RequestPacket;

public class StatusListener implements Listener {

    @PacketHandler(priority = ListenerPriority.MONITOR)
    public void onRequest(RequestPacket packet) {
        packet.reply(new ResponsePacket());
    }

    @PacketHandler(priority = ListenerPriority.MONITOR)
    public void onPing(PingPacket packet) {
        NetworkClient client = (NetworkClient) packet.getConnection();
        client.getContext().writeAndFlush(new PongPacket(packet.getPayload())).addListener(ChannelFutureListener.CLOSE);
    }

}
