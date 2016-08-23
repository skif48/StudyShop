package unit;

import com.shop.domain.user.Role;
import com.shop.domain.user.User;
import com.shop.domain.user.UserCreateForm;
import com.shop.repository.users.UserRepository;
import com.shop.service.user.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import stubs.UserRepositoryStub;

/**
 * Created by Vladyslav Usenko on 23.08.2016.
 */
public class UserServiceUnitTest {
    private final String PASSWORD = "password";
    private final String EMAIL    = "vu4848@gmail.com";

    private UserRepository userRepository;
    private UserService userService;
    private User user;
    private UserCreateForm userCreateForm;
    private String encodedPassword = new BCryptPasswordEncoder().encode(PASSWORD);

    @Before
    public void setUp() throws Exception {
        userRepository = new UserRepositoryStub();
        userService = new UserService();
        userService.setUserRepository(userRepository);

        user = new User(EMAIL, encodedPassword, Role.ADMIN);
        userCreateForm = new UserCreateForm(EMAIL, PASSWORD, PASSWORD, Role.ADMIN);
    }

    @Test
    public void createForUserCreateFormCreatesValidUser() throws Exception {
        User testUser = userService.create(userCreateForm);
        testUser.setPasswordHash(encodedPassword);
        Assert.assertEquals(user, testUser);
    }
}
