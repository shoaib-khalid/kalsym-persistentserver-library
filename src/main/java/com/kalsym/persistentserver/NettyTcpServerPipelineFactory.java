package com.kalsym.persistentserver;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import static org.jboss.netty.channel.Channels.pipeline;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

/**
 * Creates a newly configured {@link ChannelPipeline} for a new channel.
 *
 * @author <a href="http://www.jboss.org/netty/">The Netty Project</a>
 * @author <a href="http://gleamynode.net/">Trustin Lee</a>
 *
 * @version $Rev: 2080 $, $Date: 2010-01-26 18:04:19 +0900 (Tue, 26 Jan 2010) $
 *
 */
class NettyTcpServerPipelineFactory implements
        ChannelPipelineFactory {

    private ConnHandler handler;

    public NettyTcpServerPipelineFactory(ConnHandler handler) {
        this.handler = handler;
    }

    public ChannelPipeline getPipeline() throws Exception {
        // Create a default pipeline implementation.
        ChannelPipeline pipeline = pipeline();

        // Add the text line codec combination first,
        pipeline.addLast("framedecoder", new NettyTcpProtocolDecoder());
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());

        // and then business logic.
        final NettyTcpServerHandler nettyTcpServerHandler = new NettyTcpServerHandler();
        nettyTcpServerHandler.setHandler(handler);
        pipeline.addLast("handler", nettyTcpServerHandler);

        return pipeline;
    }
}
