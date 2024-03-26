package example.service;

import example.domain.entity.Tester;
import example.domain.model.JoinForm;
import example.domain.repository.TesterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TesterServiceTest {

    @Autowired
    private TesterRepository testerRepository;

    @Test
    void 중복회원_채크() {
        JoinForm joinForm = new JoinForm();
        joinForm.setEmail("tester@test.com");
        Optional<Tester> findUser = testerRepository.findByEmail(joinForm.getEmail());

        assertFalse(findUser.isEmpty(), "중복 회원이 없습니다.");
    }

    @Test
    void 로그인_체크() {

    }

}