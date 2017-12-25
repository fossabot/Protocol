package org.laxio.piston.protocol.v340.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.laxio.piston.piston.PistonServer;

import java.net.InetSocketAddress;

/**
 * Represents the server socket connected to by the clients
 */
public class NetworkServer extends Thread {

    private final PistonServer server;
    private final InetSocketAddress address;
    private EventLoopGroup boss;
    private EventLoopGroup worker;

    /**
     * Constructs a network server using the supplied address
     * @param address The address to bind to
     */
    public NetworkServer(PistonServer server, InetSocketAddress address) {
        this.server = server;
        this.address = address;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    @Override
    public void run() {
        synchronized (address) {
            boss = new NioEventLoopGroup();
            worker = new NioEventLoopGroup();

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ProtocolChannelHandler(server));

            bootstrap.bind(address).syncUninterruptibly();
        }
    }

}
