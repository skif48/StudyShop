package com.shop.service;

import com.shop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Vladyslav Usenko on 22.08.2016.
 */
@Service
public class CurrentUserDetailsService implements UserDetailsService {
    private final ShopService service;

    @Autowired
    public CurrentUserDetailsService(ShopService service) {
        this.service = service;
    }

    @Override
    public CurrentUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = service.getUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email=%s was not found", email)));
        return new CurrentUser(user);
    }
}
