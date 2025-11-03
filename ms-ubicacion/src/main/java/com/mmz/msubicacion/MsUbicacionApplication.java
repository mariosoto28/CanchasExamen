package com.mmz.msubicacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients

@SpringBootApplication
public class MsUbicacionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsUbicacionApplication.class, args);
    }

}
