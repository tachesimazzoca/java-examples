package com.github.tachesimazzoca.java.examples.netty3;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class UploadServer {
    public static void main(String[] args) {
        ChannelFactory factory = new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(), // boss
                Executors.newCachedThreadPool()  // worker
        );
        ServerBootstrap bootstrap = new ServerBootstrap(factory);
        bootstrap.setPipelineFactory(new HttpServerPipelineFactory());
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
        bootstrap.bind(new InetSocketAddress(9000));
    }

    public static class HttpServerPipelineFactory implements ChannelPipelineFactory {
        @Override
        public ChannelPipeline getPipeline() throws Exception {
            ChannelPipeline newPipeline = Channels.pipeline();
            newPipeline.addLast("tracer", new TraceHandler());
            newPipeline.addLast("decoder", new UploadFileDecoder());
            newPipeline.addLast("handler", new UploadServerHandler());

            return newPipeline;
        }
    }
}
