package org.xiaohu.nio.channel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIOClientDemo2 {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));

        Selector selector = Selector.open();
        // 注意这里是 连接成功事件
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        while (true) {
            selector.select();
            Set<SelectionKey> key = selector.selectedKeys();
            key.forEach(selectionKey -> {
                if (selectionKey.isConnectable()) {
                    // 连接成功事件
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    if (client.isConnectionPending()) {
                        try {
                            client.finishConnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                        System.out.println("连接成功");
                        ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                        executorService.submit(new Runnable() {
                            @Override
                            public void run() {
                                while (true) {
                                    byteBuffer.clear();
                                    InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                    BufferedReader b = new BufferedReader(inputStreamReader);
                                    try {
                                        String msg = b.readLine();
                                        byteBuffer.put(msg.getBytes());
                                        byteBuffer.flip();
                                        client.write(byteBuffer);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                    // 注册读取事件
                    try {
                        client.register(selector, SelectionKey.OP_READ);
                    } catch (ClosedChannelException e) {
                        e.printStackTrace();
                    }
                } else if (selectionKey.isReadable()) {
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                    while (true) {
                        byteBuffer.clear();

                        int read = 0;
                        try {
                            read = client.read(byteBuffer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (read <= 0) {
                            break;
                        }
                        byteBuffer.flip();
                        String msg = new String(byteBuffer.array(), 0, read);
                        System.out.println(msg);
                    }
                }
            });
            key.clear();
        }
    }
}
