package com.github.tachesimazzoca.java.examples.netty3;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        ChannelFactory factory = new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(), // boss
                Executors.newCachedThreadPool()  // worker
        );
        ServerBootstrap bootstrap = new ServerBootstrap(factory);
        final Class<? extends ChannelHandler> clz = TimeServerHandler.class;
        bootstrap.setPipelineFactory(
                new ChannelPipelineFactory() {
                    @Override
                    public ChannelPipeline getPipeline() throws
                            InstantiationException,
                            IllegalAccessException {
                        return Channels.pipeline(clz.newInstance());
                    }
                }
        );
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
        bootstrap.bind(new InetSocketAddress(9000));
    }
}