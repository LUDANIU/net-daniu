package com.ludaniu.netty.adapter;

import com.ludaniu.common.Packmsg;
import com.ludaniu.netty.protocol.ResponseMappingCallback;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author 鲁昊天
 * @date 2025/4/20
 */
public class ClientResponses  extends ChannelInboundHandlerAdapter {

    //consumer.....
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //1,读取
        Packmsg  packmsg = (Packmsg) msg;
        System.out.println("[client read]读取到请求头，id："+packmsg.getHeader().getRequestID());
        System.out.println("[client read]invocation的interfaceName："+packmsg.getInvocation().getInterfaceName());
        System.out.println("[client read]invocation的methodName："+packmsg.getInvocation().getMethodName());
        System.out.println("[client read]invocation的第一个参数："+packmsg.getInvocation().getParameter()[0]);
        //2，居然已经取回了packmsg，就把ResponseMappingCallback的回调函数执行了
        ResponseMappingCallback.runCallBack(packmsg);

    }
}
