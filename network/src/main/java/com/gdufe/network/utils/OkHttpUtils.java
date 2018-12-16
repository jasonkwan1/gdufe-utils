package com.gdufe.network.utils;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OkHttpUtils {

    /**
     *   OkHttpClient使用了连接池，应保持一个实例存在
     *   可以定制配置连接池
     */
    private static OkHttpClient okHttpClient = new OkHttpClient();

    private static int retryTime = 1;
    /**
     *  同步GET方法
     * @throws Exception
     */
    public static boolean syncGet(String url) throws Exception{
        Request request = new Request.Builder().url(url).build(); // 创建一个请求
        Response response = okHttpClient.newCall(request).execute(); // 返回实体
        if (response.isSuccessful()) { // 判断是否成功
            response.close();
            return true;// 打印数据
        }
        return false;
    }

    /**
     * 设置超时
     * @throws IOException
     */
    public static boolean timeOutPost(String url, String user, String password) throws IOException {
        System.out.println( "第"+ retryTime +"次重试!");
        retryTime ++;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置链接超时
                .writeTimeout(10, TimeUnit.SECONDS) // 设置写数据超时
                .readTimeout(30, TimeUnit.SECONDS) // 设置读数据超时
                .build();
        FormBody formBody = new FormBody.Builder()
                .add("DDDDD", user)
                .add("upass", password)
                .add("0MKKey", "%B5%C7%C2%BC+Login")
                .build();
        Request request = new Request.Builder().url(url)
                .addHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows 7)")
                .post(formBody).build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            System.out.println("response:" + response);
            response.close();
            return true;
        }
        return false;
    }
}
