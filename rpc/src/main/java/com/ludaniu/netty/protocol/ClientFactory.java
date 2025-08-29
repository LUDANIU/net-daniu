package com.ludaniu.netty.protocol;

import com.ludaniu.netty.adapter.ClientDecoder;
import com.ludaniu.netty.adapter.ClientResponses;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 鲁昊天
 * @version 1.0
 * @BelongsProject:rpc
 * @BelongsPackage:com.ludaniu.netty.protocol
 * @Description <p>
 * <strong>提供者：</strong>(业务组提供)
 * <p>
 * <strong>使用者：</strong>(使用业务组，若分方法描述可省略此项)
 * <p>
 * <strong>设计状态：</strong>(支出接口类设计阶段：总体，详细)
 * <p>
 * @CreatTime 2025-0411  10:17
 **/
public class ClientFactory {

    /*
     *单例factory
     */
    private static final ClientFactory factory;

    /*
     *多路复用器
     */
    private static final NioEventLoopGroup group = new NioEventLoopGroup(1);

    static {
        factory = new ClientFactory();
    }

    public static ClientFactory getFactory() {
        return factory;
    }

    ConcurrentHashMap<InetSocketAddress, ClientPool> clientPoolMap = new ConcurrentHashMap<>();

    public synchronized NioSocketChannel getClient(InetSocketAddress address) throws InterruptedException {
        if (!clientPoolMap.containsKey(address)) {
            clientPoolMap.putIfAbsent(address, new ClientPool(1));
        }
        ClientPool pool = clientPoolMap.get(address);
        /*
         *随机获取连接
         */
        double randomDouble = Math.random();  // 生成一个[0, 1)之间的随机小数
        int i = (int) (randomDouble *10 );  // 将小数转换为0到9的整数
        /*
         *获取连接
         */
        if (pool.clients[i] != null && pool.clients[i].isActive()) {
            return pool.clients[i];
        }
        /*
         *创建连接
         */
        synchronized (pool.lock[i]) {
            return pool.clients[i] = create(address);
        }
    }

    private NioSocketChannel create(InetSocketAddress address) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        ChannelFuture connect = bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();
                                pipeline.addLast(new ClientDecoder());
                                pipeline.addLast(new ClientResponses());
                            }
                        }
                ).connect(address);
        return (NioSocketChannel) connect.sync().channel();
    }
}
