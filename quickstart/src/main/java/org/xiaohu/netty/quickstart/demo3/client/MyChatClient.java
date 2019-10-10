package org.xiaohu.netty.quickstart.demo3.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MyChatClient {
    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup).channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer());
            Channel channel = bootstrap.connect("127.0.0.1", 9090).sync().channel();
            BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
            for (; ; ) {
                String ss = scanner.readLine();
                if (!"exist".equals(ss)) {
                    channel.writeAndFlush(ss + "\r\n");
                } else {
                    channel.close();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
