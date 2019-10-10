package org.xiaohu.netty.quickstart.demo2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.text.MessageFormat;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 当出现异常时
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        MessageFormat.format("remote address:{0};接受到的信息：{1}", channelHandlerContext.channel().remoteAddress(), s);
        channelHandlerContext.channel().writeAndFlush("from server:" + UUID.randomUUID());
    }
}
