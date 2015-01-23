package com.github.tachesimazzoca.java.examples.netty3;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class UploadServerHandler extends SimpleChannelHandler {
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        ChannelBuffer buffer = (ChannelBuffer) e.getMessage();

        // Dump uploaded bytes
        int n = 0;
        while (buffer.readable()) {
            byte b = buffer.readByte();
            System.out.print(String.format("%02x ", b));
            if (n % 16 == 15)
                System.out.println();
            n++;
        }
        if (n % 16 != 15)
            System.out.println();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}
