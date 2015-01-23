package com.github.tachesimazzoca.java.examples.netty3;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;

public class UploadFileDecoder extends ReplayingDecoder<UploadFileDecoder.State> {
    private int length;

    public enum State {
        Length, Body
    }

    public UploadFileDecoder() {
        super(State.Length);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx,
                            Channel ch,
                            ChannelBuffer buffer,
                            State state) throws Exception {
        switch (state) {
            case Length:
                length = buffer.readInt();
                checkpoint(State.Body);
                // Don't break here to continue reading the bytes of body.
            case Body:
                ChannelBuffer frame = buffer.readBytes(length);
                checkpoint(State.Length);
                return frame;
            default:
                throw new IllegalArgumentException("Unknown state: " + state);
        }
    }
}
