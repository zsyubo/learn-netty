package org.xiaohu.protobuf.demo1.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.xiaohu.protobuf.MyDataInfo;

public class ClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        DataInfo.Student student = DataInfo.Student.newBuilder().setAddress("美国日本")
//                .setAge(99).setName("大日本人").build();
//        System.out.println(MessageFormat.format("准备发送的数据：{0}", student.toString() ));
//        ctx.writeAndFlush( student );

        MyDataInfo.MyMessage message = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.DogType).setDog(
                MyDataInfo.Dog.newBuilder().setAge(120).setName("小几把").build()
        ).build();

        ctx.writeAndFlush(message);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }
}
