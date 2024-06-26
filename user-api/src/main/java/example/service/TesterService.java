package example.service;

import example.config.JwtProvider;
import example.domain.common.UserType;
import example.domain.entity.Tester;
import example.domain.model.JoinForm;
import example.domain.model.LoginForm;
import example.domain.repository.TesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TesterService {
    private final TesterRepository testerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public String login(LoginForm loginForm) {
        Tester tester = testerRepository.findByEmail(loginForm.getEmail()).
                orElseThrow(()-> new RuntimeException("아이디 또는 비밀번호를 확인해 주세요 - 아이디"));
        if(!passwordEncoder.matches(loginForm.getPassword(), tester.getPassword())) {
            throw new RuntimeException("아이디 또는 비밀번호를 확인해 주세요 - 비밀번호");
        }

        return jwtProvider.createToken(tester.getEmail(), tester.getId(), UserType.Tester);
    }

    public Tester join(JoinForm joinForm) {
        Optional<Tester> findTester  = testerRepository.findByEmail(joinForm.getEmail());

        if(findTester.isPresent()) {
            throw new RuntimeException("이미 존재하는 회원입니다.");
        }

        return testerRepository.save(
                Tester.builder()
                        .email(joinForm.getEmail())
                        .password(passwordEncoder.encode(joinForm.getPassword()))
                        .build());

    }

}
