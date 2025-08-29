package com.ludaniu.netty.adapter;

import com.ludaniu.common.Invocation;
import com.ludaniu.common.NettyHeader;
import com.ludaniu.common.Packmsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * @author 鲁昊天
 * @date 2025/5/1
 */
public class ClientDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> list) throws Exception {
        while (buf.readableBytes()>=100){
            /*
             * 读取请求头
             * */
            byte[] bytes=new byte[100];
            buf.getBytes(buf.readerIndex(),bytes);
            ByteArrayInputStream bis=new ByteArrayInputStream(bytes);
            ObjectInputStream ois=new ObjectInputStream(bis);
            NettyHeader header=(NettyHeader)ois.readObject();
            //判断返回的是否是net-daniu的通信协议
            if(header.getFlag()!=0X14141424){
                throw new Exception("不符合net-daniu的通信协议");
            }
            System.out.println("[client 解码器]读取到请求头，id："+header.getRequestID());
            if(buf.readableBytes()>=header.getDataLen()){
                //处理指针
                buf.readBytes(100);
                //读取请求体
                byte[] data=new byte[(int)header.getDataLen()];
                buf.readBytes(data);
                bis=new ByteArrayInputStream(data);
                ois=new ObjectInputStream(bis);
                Invocation invocation =(Invocation)ois.readObject();
                list.add(new Packmsg(header,invocation));
            }else{
                System.out.println("[client 解码器]数据不够,等下次读取");
                break;
            }
        }
    }
}
