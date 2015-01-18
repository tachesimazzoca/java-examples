package com.github.tachesimazzoca.java.examples.netty3;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class TimeDecoder extends FrameDecoder {
    private static final int SIZE = 4;
    @Override
    protected Object decode(ChannelHandlerContext channelHandlerContext,
                            Channel channel, ChannelBuffer channelBuffer) {
        if (channelBuffer.readableBytes() < SIZE)
            return null;
        return new RFC868Time(channelBuffer.readUnsignedInt());
    }
}
