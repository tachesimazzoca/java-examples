package com.github.tachesimazzoca.java.examples.netty3;

import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;

public class HttpServerHandler extends SimpleChannelHandler {
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws HttpServerException {

        Object msg = e.getMessage();
        if (msg instanceof HttpRequest) {
            System.out.println((HttpRequest) msg);
        }
        throw new HttpServerException("Server Error");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        Throwable t = e.getCause();
        if (t instanceof HttpServerException) {
            DefaultHttpResponse response = new DefaultHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.SERVICE_UNAVAILABLE);
            response.headers().set(HttpHeaders.Names.CONNECTION, "close");
            ctx.getChannel().write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            t.printStackTrace();
            ctx.getChannel().close();
        }
    }
}
