package com.shop.configuration;

import com.shop.ShopRepository;
import com.shop.ShopService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */

@Configuration
public class AppConfiguration {

    @Bean
    public ShopRepository repository(){
        return new ShopRepository();
    }

    @Bean
    public ShopService service(){
        return new ShopService();
    }
}
