package com.ludaniu.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author 鲁昊天
 * @version 1.0
 * @BelongsProject:rpc
 * @BelongsPackage:com.ludaniu.common
 * @Description <p>
 * <strong>提供者：</strong>(业务组提供)
 * <p>
 * <strong>使用者：</strong>(使用业务组，若分方法描述可省略此项)
 * <p>
 * <strong>设计状态：</strong>(支出接口类设计阶段：总体，详细)
 * <p>
 * @CreatTime 2025-0410  20:46
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NettyHeader implements Serializable {
    int flag;  //32bit可以设置很多信息。。。
    long requestID;
    long dataLen;

    public static NettyHeader createNettyHeader(byte[] msg) {
        int size = msg.length;
        int f = 0x14141414;
        long requestId= UUID.randomUUID().getLeastSignificantBits();
        return NettyHeader.builder()
                .flag(f)
                .requestID(requestId)
                .dataLen(size)
                .build();
    }
}
