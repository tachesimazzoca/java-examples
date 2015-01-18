package com.github.tachesimazzoca.java.examples.netty3;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class TimeEncoder extends SimpleChannelHandler {
    @Override
    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) {
        RFC868Time time = (RFC868Time) e.getMessage();
        ChannelBuffer buffer = ChannelBuffers.buffer(4);
        buffer.writeInt((int) time.getValue());
        Channels.write(ctx, e.getFuture(), buffer);
    }
}
