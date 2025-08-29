package com.ludaniu.common;

/**
 * @author 鲁昊天
 * @date 2025/4/20
 */
public class Packmsg {

    NettyHeader header;
    Invocation invocation;

    public NettyHeader getHeader() {
        return header;
    }

    public void setHeader(NettyHeader header) {
        this.header = header;
    }

    public Invocation getInvocation() {
        return invocation;
    }

    public void setInvocation(Invocation invocation) {
        this.invocation = invocation;
    }

    public Packmsg(NettyHeader header, Invocation invocation) {
        this.header = header;
        this.invocation = invocation;
    }
}
