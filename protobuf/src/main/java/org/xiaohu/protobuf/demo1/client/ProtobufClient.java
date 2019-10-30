package org.xiaohu.protobuf.demo1.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ProtobufClient {
    public static void main(String[] args) {
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroup).channel(NioSocketChannel.class)
                    .handler(new ProtobufClientInitializer());
            Channel channel = bootstrap.connect("127.0.0.1", 8899).sync().channel();
        } catch (Exception e) {

        } finally {
            clientGroup.shutdownGracefully();
        }
    }
}
