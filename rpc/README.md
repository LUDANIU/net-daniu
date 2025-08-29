

This project appears to implement a basic RPC (Remote Procedure Call) framework using Netty, with components for networking, serialization, service proxying, and request handling. Below is a general overview and guide for the project.

---

## 项目概述

该项目是一个基于 Netty 实现的简单远程过程调用（RPC）框架，包含以下主要功能模块：

- **网络通信**: 使用 Netty 进行高性能的异步通信。
- **协议处理**: 自定义协议头（`NettyHeader`）和消息封装（`Packmsg`）。
- **序列化/反序列化**: 提供通用的序列化工具类（`SerDerUtil`）。
- **服务注册与发现**: 使用本地注册中心（`LocalRegister`）管理接口与实现类的映射。
- **代理工厂**: 动态生成客户端代理（`ProxyFactory` 和 `NettyProxyFactory`）。
- **请求/响应处理**: 支持请求回调机制（`ResponseMappingCallback`）。

---

## 模块说明

- **Customer.java**: 主启动类，可能用于测试或运行客户端。
- **HeartBeatControllerRPC.java**: 心跳检测接口，用于客户端与服务端通信。
- **NettyTest.java**: 包含测试用例，用于启动 Netty 服务器和测试 HTTP 请求。
- **Invocation.java**: 封装远程调用的接口名、方法名、参数等信息。
- **NettyHeader.java**: Netty 通信的协议头定义，包含请求 ID、数据长度等。
- **Packmsg.java**: 消息封装类，包含协议头和调用内容。
- **ClientDecoder.java**: 客户端解码器，将接收到的字节流解码为 Packmsg 对象。
- **ClientResponses.java**: 客户端响应处理器，处理接收到的消息。
- **ClientFactory.java**: 创建 Netty 客户端的工厂类，维护连接池。
- **ClientPool.java**: 客户端连接池，复用已有的连接。
- **NettyProxyFactory.java**: 使用动态代理生成远程服务调用代理。
- **ResponseMappingCallback.java**: 管理异步请求的回调，通过请求 ID 映射到对应的 `CompletableFuture`。
- **SerDerUtil.java**: 序列化与反序列化工具类。
- **DispatcherServlet.java**: HTTP 请求的分发处理类，继承自 `HttpServlet`。
- **HttpClient.java**: HTTP 客户端，发送请求到服务端。
- **HttpServer.java**: HTTP 服务端启动类。
- **HttpServerHandler.java**: HTTP 服务端请求处理逻辑。
- **ProxyFactory.java**: 用于生成接口的动态代理类。
- **LocalRegister.java**: 本地服务注册中心，保存接口与实现类的映射。

---

## 快速开始

### 环境要求

- Java 1.8 或更高版本
- Maven（用于构建项目）
- Netty 4.x
- Servlet API（用于 HTTP 模块）

### 构建项目

```bash
mvn clean package
```

### 启动服务端

使用 `HttpServer` 或 `NettyTest` 启动服务端：

```java
public class NettyTest {
    @Test
    public void startServer() {
        // 启动 Netty 服务端逻辑
    }
}
```

### 启动客户端

使用 `Customer` 或 `NettyTest` 启动客户端并发送请求：

```java
public class Customer {
    public static void main(String[] args) throws IOException {
        // 启动客户端逻辑
    }
}
```

---

## 使用示例

### 服务端注册服务

```java
LocalRegister.register("HeartBeatControllerRPC", HeartBeatControllerRPCImpl.class);
```

### 客户端调用远程服务

```java
HeartBeatControllerRPC proxy = NettyProxyFactory.getProxy(HeartBeatControllerRPC.class);
String result = proxy.ping("Hello");
```

---

## 贡献指南

欢迎贡献代码和改进。请遵循以下步骤：

1. Fork 仓库
2. 创建新分支 (`git checkout -b feature/your-feature`)
3. 提交更改 (`git commit -am 'Add some feature'`)
4. Push 到分支 (`git push origin feature/your-feature`)
5. 提交 Pull Request

---

## 协议

本项目使用 [MIT License](https://opensource.org/licenses/MIT)，详情请查看 LICENSE 文件。

---

## 联系方式

如有问题或建议，请在 Gitee 上提交 issue 或联系项目维护者。