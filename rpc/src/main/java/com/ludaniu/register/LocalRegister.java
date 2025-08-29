package com.ludaniu.register;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 鲁昊天
 * @date 2025/4/6
 */
public class LocalRegister {
    private static Map<String, Class> registerMap = new HashMap<>();
    public static void register(String interfaceName, Class implClass) {
        registerMap.put(interfaceName, implClass);
    }
    public static Class get(String interfaceName) {
        return registerMap.get(interfaceName);
    }
}
