package example.service;

import example.domain.entity.User;
import example.domain.model.JoinForm;
import example.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Test
    void 회원가입_테스트() {
        JoinForm joinForm = new JoinForm();
        joinForm.setEmail("test@test.com");
        joinForm.setPassword("123123");

        User user = userService.join(joinForm);

        JoinForm joinForm2 = new JoinForm();
        joinForm2.setEmail("test@test.com");
        joinForm2.setPassword("123123");

//        User secondUser = userService.join(joinForm2);
        Optional<User> findUser = userRepository.findByEmail(joinForm2.getEmail());

        // 이미 있는 이메일이 들어갔을때 검증
        assertEquals(user.getEmail(), findUser);

    }

}