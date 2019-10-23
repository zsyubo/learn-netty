package org.xiaohu.nio.buffer.demo1;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferReadTest {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("buffer.txt");
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        channel.read(byteBuffer);
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            byte b = byteBuffer.get();
            System.out.println("Read: " + (char) b);
        }
    }
}
