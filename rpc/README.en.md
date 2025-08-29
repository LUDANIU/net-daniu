This project appears to implement a basic RPC (Remote Procedure Call) framework using Netty, with components for networking, serialization, service proxying, and request handling. Below is a general overview and guide for the project.

---

## Project Overview

This project is a simple Remote Procedure Call (RPC) framework implemented based on Netty, containing the following major functional modules:

- **Network Communication**: High-performance asynchronous communication using Netty.
- **Protocol Processing**: Custom protocol header (`NettyHeader`) and message encapsulation (`Packmsg`).
- **Serialization/Deserialization**: Provides a generic serialization utility class (`SerDerUtil`).
- **Service Registration and Discovery**: Manages mapping between interfaces and implementation classes using a local registry (`LocalRegister`).
- **Proxy Factory**: Dynamically generates client proxies (`ProxyFactory` and `NettyProxyFactory`).
- **Request/Response Handling**: Supports request callback mechanism (`ResponseMappingCallback`).

---

## Module Description

- **Customer.java**: Main startup class, possibly used for testing or running the client.
- **HeartBeatControllerRPC.java**: Heartbeat detection interface, used for client-server communication.
- **NettyTest.java**: Contains test cases for starting the Netty server and testing HTTP requests.
- **Invocation.java**: Encapsulates remote call information such as interface name, method name, and parameters.
- **NettyHeader.java**: Definition of the Netty communication protocol header, including request ID, data length, etc.
- **Packmsg.java**: Message encapsulation class, containing the protocol header and call content.
- **ClientDecoder.java**: Client decoder that decodes received byte streams into Packmsg objects.
- **ClientResponses.java**: Client response handler for processing received messages.
- **ClientFactory.java**: Factory class for creating Netty clients, maintaining a connection pool.
- **ClientPool.java**: Client connection pool for reusing existing connections.
- **NettyProxyFactory.java**: Generates remote service call proxies using dynamic proxy.
- **ResponseMappingCallback.java**: Manages callbacks for asynchronous requests by mapping request IDs to corresponding `CompletableFuture`.
- **SerDerUtil.java**: Serialization and deserialization utility class.
- **DispatcherServlet.java**: HTTP request dispatching handler, inherited from `HttpServlet`.
- **HttpClient.java**: HTTP client for sending requests to the server.
- **HttpServer.java**: HTTP server startup class.
- **HttpServerHandler.java**: HTTP server request handling logic.
- **ProxyFactory.java**: Used to generate dynamic proxy classes for interfaces.
- **LocalRegister.java**: Local service registry that stores mappings between interfaces and implementation classes.

---

## Quick Start

### Prerequisites

- Java 1.8 or higher
- Maven (for building the project)
- Netty 4.x
- Servlet API (for HTTP module)

### Build the Project

```bash
mvn clean package
```

### Start the Server

Use `HttpServer` or `NettyTest` to start the server:

```java
public class NettyTest {
    @Test
    public void startServer() {
        // Logic to start the Netty server
    }
}
```

### Start the Client

Use `Customer` or `NettyTest` to start the client and send requests:

```java
public class Customer {
    public static void main(String[] args) throws IOException {
        // Logic to start the client
    }
}
```

---

## Usage Example

### Server-side Service Registration

```java
LocalRegister.register("HeartBeatControllerRPC", HeartBeatControllerRPCImpl.class);
```

### Client-side Remote Service Invocation

```java
HeartBeatControllerRPC proxy = NettyProxyFactory.getProxy(HeartBeatControllerRPC.class);
String result = proxy.ping("Hello");
```

---

## Contribution Guide

Contributions of code and improvements are welcome. Please follow these steps:

1. Fork the repository
2. Create a new branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Submit a Pull Request

---

## License

This project uses the [MIT License](https://opensource.org/licenses/MIT). For details, please refer to the LICENSE file.

---

## Contact

If you have any questions or suggestions, please submit an issue on Gitee or contact the project maintainer.