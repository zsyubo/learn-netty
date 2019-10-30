package org.xiaohu.nio.buffer.demo2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileCopy {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("buffer.txt");
        FileOutputStream outputStream = new FileOutputStream("buffer2.txt");

        FileChannel input = inputStream.getChannel();
        FileChannel output = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
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
    }
}
