package org.xiaohu.netty.quickstart.demo1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty编程步骤：
 * 1. 创建2个EventLoopGroup
 * 2. 创建自己的ChannelInitializer(管道初始化做需要进行的一些操作，比如编解码器)
 * 3. 编写事件回调方法的业务逻辑。
 *
 * @author hyf
 * @date 2019/10/9
 **/
public class TestServer {

    public static void main(String[] args) throws InterruptedException {
        // 接口客户顿连接，但是不做业务处理，只是处理连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 业务处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 启动封装
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInit());
            // 绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
