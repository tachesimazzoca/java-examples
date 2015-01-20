package com.github.tachesimazzoca.java.examples.netty3;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class TimeClient {
    public static void main(String[] args) {
        ChannelFactory factory = new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(), // boss
                Executors.newCachedThreadPool()  // worker
        );
        ClientBootstrap bootstrap = new ClientBootstrap(factory);
        bootstrap.setPipelineFactory(
                new ChannelPipelineFactory() {
                    @Override
                    public ChannelPipeline getPipeline() {
                        return Channels.pipeline(
                                new TraceHandler(),
                                new TimeDecoder(),
                                new TimeClientHandler());
                    }
                }
        );
        bootstrap.setOption("tcpNoDelay", true);
        bootstrap.setOption("keepAlive", true);

        ChannelFuture f = bootstrap.connect(new InetSocketAddress("localhost", 9000));
        f.awaitUninterruptibly();
        if (!f.isSuccess())
            f.getCause().printStackTrace();
        f.getChannel().getCloseFuture().awaitUninterruptibly();
        factory.releaseExternalResources();
    }
}
