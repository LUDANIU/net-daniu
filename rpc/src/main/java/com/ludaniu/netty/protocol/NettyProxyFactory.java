package com.ludaniu.netty.protocol;

import com.ludaniu.common.Invocation;
import com.ludaniu.common.NettyHeader;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author 鲁昊天
 * @version 1.0
 * <strong>提供者：</strong>(业务组提供)
 * <p>
 * <strong>使用者：</strong>(使用业务组，若分方法描述可省略此项)
 * <p>
 * <strong>设计状态：</strong>(支出接口类设计阶段：总体，详细)
 * <p>
 * @CreatTime 2025-0410  17:27
 **/
@SuppressWarnings("unchecked")
public class NettyProxyFactory {
    public static <T> T getProxy(Class<T> interfaceClass) {

        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        /*
                         *1，封装成Invocation
                         */
                        String methodName = method.getName();
                        String className = interfaceClass.getSimpleName();
                        Class[] parameterTypes = method.getParameterTypes();
                        Invocation invocation = new Invocation(className, methodName, args, parameterTypes, null);
                        /*
                         *2，序列化请求头和请求体
                         */
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                        objectOutputStream.writeObject(invocation);
                        byte[] msgByteArray = byteArrayOutputStream.toByteArray();
                        /*
                         *3，请求头
                         */
                        int flag = 0x14141414;
                        long requestID = UUID.randomUUID().getMostSignificantBits();
                        long dataLen = msgByteArray.length;
                        NettyHeader nettyHeader = new NettyHeader(flag, requestID, dataLen);
                        /*
                         *4，请求头序列化
                         */
                        byteArrayOutputStream.reset();
                        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                        objectOutputStream.writeObject(nettyHeader);
                        byte[] headerByteArray = byteArrayOutputStream.toByteArray();
                        /*
                        * 5，拼接请求
                        * */
                        ByteBuf byteBuf= PooledByteBufAllocator.DEFAULT.directBuffer(msgByteArray.length+headerByteArray.length);
                        long id = nettyHeader.getRequestID();
                        CompletableFuture<String> res = new CompletableFuture<>();
                        ResponseMappingCallback.addCallBack(id, res);
                        byteBuf.writeBytes(headerByteArray);
                        byteBuf.writeBytes(msgByteArray);
                        System.out.println("请求头长度："+headerByteArray.length);
                        System.out.println("请求体长度："+msgByteArray.length);
                        /*
                        * 6，发送请求
                        * */
                        NioSocketChannel clientChannel = ClientFactory.getFactory().getClient(new InetSocketAddress("localhost", 9090));
                        ChannelFuture channelFuture = clientChannel.writeAndFlush(byteBuf);
                        channelFuture.sync();

                        return res.get();//阻塞的
                    }
                }


        );
    }
}
