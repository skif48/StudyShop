package com.shop.service.userServices;

import com.shop.domain.user.Role;
import com.shop.domain.user.User;
import com.shop.domain.user.UserCreateForm;
import com.shop.repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Vladyslav Usenko on 22.08.2016.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService() {
    }

    //TODO 22.08.2016 unit test
    public Optional<User> getUserById(long id) {
        return Optional.ofNullable(userRepository.findOne(id));
    }

    //TODO 22.08.2016 unit test
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    //TODO 22.08.2016 unit test
    public Collection<User> getAllUsers() {
        return userRepository.findAll(new Sort("email"));
    }

    //TODO 22.08.2016 unit test
    public User create(UserCreateForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setPasswordHash(new BCryptPasswordEncoder().encode(form.getPassword()));
        user.setRole(form.getRole());
        return userRepository.save(user);
    }

    //TODO 22.08.2016 unit test
    public boolean canAccessUser(CurrentUser currentUser, Long userId) {
        return currentUser != null
                && (currentUser.getRole() == Role.ADMIN || currentUser.getId().equals(userId));
    }
}
