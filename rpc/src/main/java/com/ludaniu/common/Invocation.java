package com.ludaniu.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 鲁昊天
 * @date 2025/4/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invocation implements Serializable {
    private static final long serialVersionUID = 4687666329051294325L;
    private String interfaceName;
    private String methodName;
    private Object[] parameter;
    private Class[] parameterTypes;
    private String res;
}
