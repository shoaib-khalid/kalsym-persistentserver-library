package com.kalsym.persistentserver;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

class NettyTcpServerHandler extends SimpleChannelUpstreamHandler {

    private ConnHandler handler;

    public void setHandler(ConnHandler handler) {
        this.handler = handler;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        // Send back the received message to the remote peer.
        String buf = (String) e.getMessage();
        //byte[] bufbyte=buf.array();
        handler.handleMessage(buf, e.getChannel());
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx,
            ChannelStateEvent e) {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        handler.exceptionError(e.toString());
    }

}
