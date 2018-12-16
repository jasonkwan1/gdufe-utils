package com.gdufe.network;

import com.gdufe.network.service.NetworkService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class NetworkApplication {

    public static void main(String[] args) {
        System.out.println("\r\n\r\n\r\n------------------------Code written by jasonKwan------------------------\r\n\r\n\r\n");
        System.out.println("network service is started!");
        int retryTime = 1;
        String propertiesPath = System.getProperty("user.dir") + "\\config.properties";
        Properties properties = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(propertiesPath);
        }catch (Exception e){
            System.out.println("找不到config.properties文件");
        }
        try {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("读取config.properties文件失败");
        }
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        System.out.println("账号:"+ user +", 密码:" + password);
        while (true) {
            try {
                boolean isConect = NetworkService.isConnect();
                if (!isConect) {
                    long startTime = System.currentTimeMillis();
                    System.out.println("network is broken and reconnect!");
                    boolean isReConnect = NetworkService.reConnect(user, password);
                    while (!isReConnect) {
                        isReConnect = NetworkService.reConnect(user, password);
                    }
                    long endTime = System.currentTimeMillis();
                    long cost = endTime - startTime;
                    System.out.println("network is connected, cost:" + cost + "ms");
                }
                Thread.currentThread().sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
