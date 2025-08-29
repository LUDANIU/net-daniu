package com.ludaniu.netty.protocol;

import com.ludaniu.common.Packmsg;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 鲁昊天
 * @date 2025/4/20
 */

public class ResponseMappingCallback {
    static ConcurrentHashMap<Long, CompletableFuture> mapping = new ConcurrentHashMap<>();

    public static void  addCallBack(long requestID,CompletableFuture cb){
        mapping.putIfAbsent(requestID,cb);
    }
    public static void runCallBack(Packmsg msg){
        CompletableFuture cf = mapping.get(msg.getHeader().getRequestID());
        cf.complete(msg.getInvocation().getRes());
        removeCB(msg.getHeader().getRequestID());

    }

    private static void removeCB(long requestID) {
        mapping.remove(requestID);
    }

}