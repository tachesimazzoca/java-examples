package com.github.tachesimazzoca.java.examples.netty3;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelDownstreamHandler;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ChannelUpstreamHandler;
import org.jboss.netty.channel.ChildChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.WriteCompletionEvent;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;

import java.net.SocketAddress;

public class TraceHandler implements ChannelUpstreamHandler, ChannelDownstreamHandler {
    private static final InternalLogger logger =
            InternalLoggerFactory.getInstance(TraceHandler.class.getName());

    private StringBuilder prepareTraceStringBuilder(ChannelEvent e) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(Thread.currentThread().getName());
        sb.append(":");
        Channel ch = e.getChannel();
        sb.append(String.format("%08x", ch.getId()));
        sb.append("] ");

        SocketAddress remote = ch.getRemoteAddress();
        if (null != remote) {
            sb.append(ch.getRemoteAddress());
            sb.append(" ");
        } else {
            sb.append("- ");
        }

        int op = ch.getInterestOps();
        switch (ch.getInterestOps()) {
            case Channel.OP_NONE:
                sb.append("OP_NONE ");
                break;
            case Channel.OP_READ:
                sb.append("OP_READ ");
                break;
            case Channel.OP_READ_WRITE:
                sb.append("OP_READ_WRITE ");
                break;
            case Channel.OP_WRITE:
                sb.append("OP_WRITE ");
                break;
            default:
                sb.append("- ");
                break;
        }

        sb.append(e.getClass().getSimpleName());
        sb.append(" ");
        return sb;
    }

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        StringBuilder sb = prepareTraceStringBuilder(e);

        if (e instanceof MessageEvent) {
            // A message object (e.g. ChannelBuffer) was received from a remote peer.
            sb.append("messageReceived: ");
            MessageEvent evt = (MessageEvent) e;
            Object msg = evt.getMessage();
            sb.append(msg.getClass().getSimpleName());
            if (msg instanceof ChannelBuffer) {
                sb.append(" - ");
                sb.append(((ChannelBuffer) msg).readableBytes());
                sb.append(" bytes are readable.");
            }

        } else if (e instanceof WriteCompletionEvent) {
            // Something has been written to a remote peer.
            sb.append("writeComplete:");
            WriteCompletionEvent evt = (WriteCompletionEvent) e;
            sb.append(evt.getWrittenAmount());
            sb.append(" bytes have been written.");

        } else if (e instanceof ChildChannelStateEvent) {
            // These two additional event types are used only for a parent channel
            // which can have a child channel (e.g. ServerSocketChannel).
            ChildChannelStateEvent evt = (ChildChannelStateEvent) e;
            if (evt.getChildChannel().isOpen()) {
                // A child Channel was open (e.g. a server channel accepted a connection.)
                sb.append("childChannelOpen");
            } else {
                // A child Channel was closed (e.g. the accepted connection was closed.)
                sb.append("childChannelClosed");
            }

        } else if (e instanceof ChannelStateEvent) {
            ChannelStateEvent evt = (ChannelStateEvent) e;
            switch (evt.getState()) {
                case OPEN:
                    if (Boolean.TRUE.equals(evt.getValue())) {
                        // A Channel is open, but not bound nor connected.
                        sb.append("channelOpen");
                    } else {
                        // A Channel was closed and all its related resources were released.
                        sb.append("channelClosed");
                    }
                    break;
                case BOUND:
                    if (evt.getValue() != null) {
                        // A Channel is open and bound to a local address, but not connected.
                        sb.append("channelBound: local ");
                        sb.append(evt.getValue());
                    } else {
                        // A Channel was unbound from the current local address.
                        sb.append("channelUnbound");
                    }
                    break;
                case CONNECTED:
                    if (evt.getValue() != null) {
                        // A Channel is open, bound to a local address,
                        // and connected to a remote address.
                        sb.append("channelConnected: remote ");
                        sb.append(evt.getValue());
                    } else {
                        // A Channel was disconnected from its remote peer.
                        sb.append("channelDisconnected");
                    }
                    break;
                case INTEREST_OPS:
                    // A Channel's interestOps was changed.
                    sb.append("channelInterestChanged");
                    break;
                default:
                    break;
            }

        } else if (e instanceof ExceptionEvent) {
            // An exception was raised by an I/O thread or a ChannelHandler.
            sb.append("exceptionCaught: ");
            ExceptionEvent evt = (ExceptionEvent) e;
            sb.append(evt.getCause());
        }

        logger.info(sb.toString());
        ctx.sendUpstream(e);
    }

    @Override
    public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        StringBuilder sb = prepareTraceStringBuilder(e);

        if (e instanceof MessageEvent) {
            // A message object (e.g. ChannelBuffer) was received from a remote peer.
            sb.append("writeRequested: ");
            MessageEvent evt = (MessageEvent) e;
            Object msg = evt.getMessage();
            sb.append(msg.getClass().getSimpleName());
            if (msg instanceof ChannelBuffer) {
                sb.append(" - ");
                sb.append(((ChannelBuffer) msg).readableBytes());
                sb.append(" bytes are readable.");
            }

        } else if (e instanceof ChannelStateEvent) {
            ChannelStateEvent evt = (ChannelStateEvent) e;
            switch (evt.getState()) {
                case OPEN:
                    if (!Boolean.TRUE.equals(evt.getValue())) {
                        // Close the Channel.
                        sb.append("closeRequested");
                    }
                    break;
                case BOUND:
                    if (evt.getValue() != null) {
                        // Bind the Channel to the specified local address.
                        sb.append("bindRequested: local ");
                        sb.append(evt.getValue());
                    } else {
                        // Unbind the Channel from the current local address.
                        sb.append("unbindRequested");
                    }
                    break;
                case CONNECTED:
                    if (evt.getValue() != null) {
                        // Connect the Channel to the specified remote address.
                        sb.append("connectRequested: remote ");
                        sb.append(evt.getValue());
                    } else {
                        // Disconnect the Channel from the current remote address.
                        sb.append("disconnectRequested");
                    }
                    break;
                case INTEREST_OPS:
                    sb.append("setInterestOpsRequested: ");
                    break;
                default:
                    break;
            }
        }

        logger.info(sb.toString());
        ctx.sendDownstream(e);
    }
}
