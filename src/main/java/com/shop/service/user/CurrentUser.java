package com.shop.service.user;


import com.shop.domain.user.Role;
import com.shop.domain.user.User;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Created by Vladyslav Usenko on 22.08.2016.
 */
public class CurrentUser extends org.springframework.security.core.userdetails.User {
    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPasswordHash(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

    public Role getRole() {
        return user.getRole();
    }
}
