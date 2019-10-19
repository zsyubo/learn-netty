package org.xiaohu.protobuf.demo1.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.xiaohu.protobuf.MyDataInfo;

public class MyServerHandle extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
//        System.out.println(MessageFormat.format("收到的数据：{0}", msg.toString() ));

        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
        if (dataType == MyDataInfo.MyMessage.DataType.DogType) {
            MyDataInfo.Dog dog = msg.getDog();
            System.out.println("dog:" + dog.getName());
            System.out.println("dog:" + dog.getAge());
        }
    }
}
