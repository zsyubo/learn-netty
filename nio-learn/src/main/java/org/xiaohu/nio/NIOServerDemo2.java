package org.xiaohu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.*;

public class NIOServerDemo2 {

    static Map<String, SocketChannel> channelMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            selector.select();
            Set<SelectionKey> kes = selector.selectedKeys();

            Iterator<SelectionKey> it = kes.iterator();
            while (it.hasNext()) {
                SelectionKey selectionKey = it.next();

                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel channel = serverSocketChannel1.accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);

                    String key = "[" + UUID.randomUUID().toString() + "]";
                    System.out.println("客户端：" + channel + " 已连接！");
                    channelMap.put(key, channel);
                } else if (selectionKey.isReadable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                    while (true) {
                        byteBuffer.clear();

                        int read = channel.read(byteBuffer);
                        if (read <= 0) {
                            break;
                        }
                        byteBuffer.flip();
                        Charset charset = Charset.forName("utf-8");
                        String msg = String.valueOf(charset.decode(byteBuffer).array());
                        String clientName = "";
                        for (Map.Entry<String, SocketChannel> kk : channelMap.entrySet()) {
                            SocketChannel client = kk.getValue();
                            if (channel == client) {
                                clientName = kk.getKey();
                            }
                        }
                        msg = clientName + msg;
                        for (Map.Entry<String, SocketChannel> kk : channelMap.entrySet()) {
                            SocketChannel client = kk.getValue();
                            if (channel == client) {
                                continue;
                            }
                            ByteBuffer byteBuffer1 = ByteBuffer.allocate(1024);
                            byteBuffer1.put(msg.getBytes());
                            byteBuffer1.flip();
                            client.write(byteBuffer1);
                        }
                    }
                }
            }
            it.remove();
        }
    }
}
