package com.ludaniu.protocol;

import com.ludaniu.common.Invocation;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 鲁昊天
 * @date 2025/4/6
 */
public class HttpClient {
    public String send(String hostname, Integer port,Invocation invocation) throws IOException {
        URL url=new URL("http",hostname,port,"/");
        HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        //配置
        OutputStream outputStream=httpURLConnection.getOutputStream();
        ObjectOutputStream oos=new ObjectOutputStream(outputStream);
        //
        oos.writeObject(invocation);
        oos.flush();
        oos.close();

        //
        InputStream inputStream=httpURLConnection.getInputStream();
        String result=IOUtils.toString(inputStream);
        return result;
    }
}
