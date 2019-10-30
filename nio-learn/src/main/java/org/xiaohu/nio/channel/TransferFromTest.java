package org.xiaohu.nio.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class TransferFromTest {
    public static void main(String[] args) throws IOException {
        // macos 创建一个大文件 mkfile -n 5g ~/Downloads/5GB.txt
        long start = System.currentTimeMillis();
//        RandomAccessFile fromFile = new RandomAccessFile("/Users/huyuanfu/Downloads/5GB.txt", "rw");
//        RandomAccessFile toFile = new RandomAccessFile("/Users/huyuanfu/Downloads/5GB-2.txt", "rw");

        RandomAccessFile fromFile = new RandomAccessFile("/Users/huyuanfu/Downloads/spring-1.mp4", "rw");
        RandomAccessFile toFile = new RandomAccessFile("/Users/huyuanfu/Downloads/spring-3.mp4", "rw");

        FileChannel fromChannel = fromFile.getChannel();
        FileChannel toChannel = toFile.getChannel();
        System.out.println("拷贝字节：" + fromChannel.size());
        toChannel.transferFrom(fromChannel, 0, fromChannel.size());
//        fromChannel.transferTo(0, fromChannel.size() , toChannel );
        long end = System.currentTimeMillis();

        System.out.println("cost time:" + (end - start));
        ///Users/huyuanfu/Downloads

        //拷贝字节：1439928644
        //cost time:8064   8秒多

        // 拷贝字节：5368709120
        //cost time:5202
    }
}
