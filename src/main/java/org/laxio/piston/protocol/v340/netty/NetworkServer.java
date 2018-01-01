/*-
 * ========================LICENSE_START========================
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
 * ========================LICENSE_END========================
 */
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

    private final StartRequest request;
    private EventLoopGroup boss;
    private EventLoopGroup worker;

    /**
     * Constructs a network server using the supplied address
     *
     * @param address The address to bind to
     */
    public NetworkServer(PistonServer server, InetSocketAddress address) {
        this.server = server;
        this.address = address;
        this.request = new StartRequest();
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public StartRequest getRequest() {
        return request;
    }

    @Override
    public synchronized void start() {
        request.setStarted(false);
        super.start();
    }

    @Override
    public void run() {
        synchronized (address) {
            try {
                boss = new NioEventLoopGroup();
                worker = new NioEventLoopGroup();
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(boss, worker)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ProtocolChannelHandler(server));

                bootstrap.bind(address).sync();
                request.setStarted(true);
            } catch (Exception ex) {
                server.handle(ex);
            }
        }
    }

    public synchronized void shutdown() {
        if (boss == null || worker == null) {
            return;
        }

        boss.shutdownGracefully();
        worker.shutdownGracefully();
        boss = null;
        worker = null;
    }

}
