package com.ludaniu.protocol;

import com.ludaniu.common.Invocation;
import com.ludaniu.register.LocalRegister;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 鲁昊天
 * @date 2025/4/5
 */
/*
public class HttpServerHandler {
    public void handle(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Invocation invocation=(Invocation) new ObjectInputStream(req.getInputStream()).readObject();
            //获取接口名
            String interfaceName=invocation.getInterfaceName();
            //获取实现类
            Class implClass= LocalRegister.get(interfaceName);
            Method method = implClass.getMethod(invocation.getMethodName(), invocation.getParameterTypes());
            String invokeResult = (String)method.invoke(implClass.newInstance(), invocation.getParameter());
            IOUtils.write(invokeResult,resp.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
*/
public class HttpServerHandler {

    public void handle(HttpServletRequest req, HttpServletResponse resp) {

        try {
            Invocation invocation = (Invocation) new ObjectInputStream(req.getInputStream()).readObject();
            String interfaceName = invocation.getInterfaceName();
            Class implClass = LocalRegister.get(interfaceName);
            Method method = implClass.getMethod(invocation.getMethodName(), invocation.getParameterTypes());
            String result = (String) method.invoke(implClass.newInstance(), invocation.getParameter());

            System.out.println("tomcat:" + result);
            IOUtils.write(result, resp.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}