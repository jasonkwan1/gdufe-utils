package com.gdufe.network.service;

import com.gdufe.network.utils.OkHttpUtils;
import org.springframework.stereotype.Component;

public class NetworkService {

    public static boolean isConnect(){
        boolean bool = false;
        try{
           bool = OkHttpUtils.syncGet("https://www.baidu.com/");
        }catch (Exception e){
            System.out.println("check isConect error e = " + e.getMessage());
        }
        return bool;
    }

    public static boolean reConnect(String user, String password){
        boolean bool = false;
        try {
            bool = OkHttpUtils.timeOutPost("http://58.62.247.115", user, password);
        }catch (Exception e){
            System.out.println("reconnect error e = " + e.getMessage());
        }
        return bool;
    }
}
