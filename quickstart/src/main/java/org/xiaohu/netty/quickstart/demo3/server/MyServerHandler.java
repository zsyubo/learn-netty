package org.xiaohu.netty.quickstart.demo3.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.MessageFormat;

public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 存放已建立连接的channel，底层是一个set
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        final Channel channel = ctx.channel();
        System.out.println((MessageFormat.format("收到客户端数据：{0}：{1}", channel.remoteAddress(), msg)));
        channelGroup.forEach(ch -> {
            if (channel == ch) {
                ch.writeAndFlush(MessageFormat.format("本机：{0} \n", msg));
            } else {
                ch.writeAndFlush(MessageFormat.format("{0}：{1} \n", channel.remoteAddress(), msg));
            }

        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(MessageFormat.format("[客户端]-{0}加入\n", channel.remoteAddress()));
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(MessageFormat.format("[客户端]-{0}离开\n", channel.remoteAddress()));
        // 当连接断掉之后， netty会自动remove
//        channelGroup.remove( channel );
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println((MessageFormat.format("[客户端]-{0}上线\n", channel.remoteAddress())));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println((MessageFormat.format("[客户端]-{0}下线\n", channel.remoteAddress())));
    }
}
