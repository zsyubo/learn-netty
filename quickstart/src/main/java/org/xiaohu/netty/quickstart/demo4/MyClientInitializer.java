package org.xiaohu.netty.quickstart.demo4;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        // 空闲状态的处理器
        // 读空闲5秒， 比如客户端连接过了5秒内没法送任何数据。
        // 写空闲7秒。 服务端在收到客户端数据7秒内没收到任何写数据。
        // 读写空闲10秒。如果要想测试读写空闲则需要把读写空闲时间比读/写空闲的时间短，否则不会触发。读写空闲的意思的
        // 距上次收到读/写请求后，空闲时间内没收读/写任一事件。
        channelPipeline.addLast(new IdleStateHandler(5, 7, 10, TimeUnit.SECONDS));
        channelPipeline.addLast(new MyServerHandler());
    }
}
