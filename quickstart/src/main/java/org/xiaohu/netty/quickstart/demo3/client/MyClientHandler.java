package org.xiaohu.netty.quickstart.demo3.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 处理从服务端收到的消息
     *
     * @param channelHandlerContext
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

        // 关闭连接
//        channelHandlerContext.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接成功：" + new Date());
    }
}
