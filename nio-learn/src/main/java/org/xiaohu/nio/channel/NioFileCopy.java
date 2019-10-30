package org.xiaohu.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileCopy {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        FileInputStream inputStream = new FileInputStream("/Users/huyuanfu/Downloads/5GB.txt");
        FileOutputStream outputStream = new FileOutputStream("/Users/huyuanfu/Downloads/5GB-2.txt");

        FileChannel input = inputStream.getChannel();
        FileChannel output = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        while (true) {
            byteBuffer.clear();
            //read()方法返回的int代表着有多少数据被读入了Buffer。如果返回-1，代表着已经读到文件结尾。
            int read = input.read(byteBuffer);
            if (-1 == read) {
                break;
            }
            byteBuffer.flip();
            output.write(byteBuffer);
            outputStream.flush();
        }
        long end = System.currentTimeMillis();
        System.out.println("cost time:" + (end - start));
        // cost time:6837

        // cost time:27602
        // cost time:25564
    }
}
