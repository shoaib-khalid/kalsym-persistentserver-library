package com.kalsym.persistentserver;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;

class NettyTcpProtocolDecoder extends DelimiterBasedFrameDecoder {

    public NettyTcpProtocolDecoder() {
        //1 param : maxFrameLength - the maximum length of the decoded frame. A TooLongFrameException is thrown if the length of the frame exceeds this value.
        //2 param : stripDelimiter - whether the decoded frame should strip out the delimiter or not
        //3 param : delimiters - the delimiters
        super(8192, true, Delimiters.lineDelimiter());
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx,
            Channel channel,
            ChannelBuffer buffer)
            throws Exception {
        return super.decode(ctx, channel, buffer);
    }

}
