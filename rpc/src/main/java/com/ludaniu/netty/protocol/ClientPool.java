package com.ludaniu.netty.protocol;

import io.netty.channel.socket.nio.NioSocketChannel;

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
 * @CreatTime 2025-0411  10:10
 **/
public class ClientPool {
    NioSocketChannel[] clients;
    Boolean[] lock;

    public ClientPool(int size) {
        clients = new NioSocketChannel[size];
        lock = new Boolean[size];
        for (int i = 0; i < size; i++) {
            lock[i] = true;
        }
    }

    public Integer getSize() {
        return clients.length;
    }
}
