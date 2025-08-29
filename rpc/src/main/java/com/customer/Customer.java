package com.customer;

import com.ludaniu.netty.protocol.NettyProxyFactory;

import java.io.IOException;

/**
 * @author 鲁昊天
 * @date 2025/4/6
 */
public class Customer {
    public static void main(String[] args) throws IOException {
        int size = 20;
        Thread[] threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            int finalI = i;
            threads[i] = new Thread(()->{
               HeartBeatControllerRPC heartBeat = NettyProxyFactory.getProxy(HeartBeatControllerRPC.class);
               String request = "ludaniu" + finalI;
                String response = heartBeat.heartBeat(request);
                System.out.println("发送请求："+request+"返回："+response);
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
        
    }
}
