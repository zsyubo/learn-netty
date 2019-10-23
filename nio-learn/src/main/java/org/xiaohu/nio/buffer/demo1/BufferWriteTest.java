package org.xiaohu.nio.buffer.demo1;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferWriteTest {
    public static void main(String[] args) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("buffer.txt");
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        byte[] bytes = "CNM funck".getBytes();
        byteBuffer.put(bytes);

        byteBuffer.flip();

        fileChannel.write(byteBuffer);
        fileOutputStream.flush();

        fileOutputStream.close();
    }
}
