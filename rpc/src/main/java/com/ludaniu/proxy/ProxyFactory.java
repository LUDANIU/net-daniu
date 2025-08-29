package com.ludaniu.proxy;

import com.ludaniu.common.Invocation;
import com.ludaniu.protocol.HttpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author 鲁昊天
 * @date 2025/4/6
 */
public class ProxyFactory {
    @SuppressWarnings("unchecked")
    public static <T> T getProxy(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),  // 类加载器
                new Class[]{interfaceClass},  // 被代理对象实现的接口
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//

                        Invocation invocation = new Invocation(interfaceClass.getSimpleName(), method.getName(), args,
                                new Class[]{String.class}, null);
                        HttpClient httpClient = new HttpClient();
                        return httpClient.send("127.0.0.1", 8080, invocation);
                    }
                } // 代理逻辑
        );
    }
}
