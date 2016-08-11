package com.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */

@Configuration
public class AppConfiguration {

    @Bean
    public ShopRepository repository(){
        return new ShopRepository();
    }
}
