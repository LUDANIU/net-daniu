package com.ludaniu.netty.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author 鲁昊天
 * @date 2025/5/1
 */
public class SerDerUtil {
    static ByteArrayOutputStream out = new ByteArrayOutputStream();

    public synchronized  static byte[] ser(Object msg){
        out.reset();
        ObjectOutputStream oout = null;
        byte[] msgBody = null;
        try {
            oout = new ObjectOutputStream(out);
            oout.writeObject(msg);
            msgBody= out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return msgBody;


    }
}
