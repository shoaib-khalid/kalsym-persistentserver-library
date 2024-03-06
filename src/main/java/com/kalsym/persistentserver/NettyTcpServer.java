package com.kalsym.persistentserver;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

class NettyTcpServer implements AppServer {

    private ServerBootstrap bootstrap;

    @Override
    public void start(int port, ConnHandler handler) {
        // Configure the server.
        bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

		// Set up the pipeline factory.
		/*final NettyTcpServerHandler nettyTcpServerHandler=new NettyTcpServerHandler();
         nettyTcpServerHandler.SetHandler(handler);
         bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
         public ChannelPipeline getPipeline() throws Exception {
         return Channels.pipeline(
                                                
         new NettyTcpProtocolDecoder(),
         nettyTcpServerHandler);
         }
         });*/
        // Configure the pipeline factory.
        bootstrap.setPipelineFactory(new NettyTcpServerPipelineFactory(handler));

        // Bind and start to accept incoming connections.
        bootstrap.bind(new InetSocketAddress(port));
    }

}
