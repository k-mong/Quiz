package example.service;

import example.client.RedisClient;
import example.config.JwtProvider;
import example.domain.common.UserType;
import example.domain.entity.User;
import example.domain.model.JoinForm;
import example.domain.model.LoginForm;
import example.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RedisClient redisClient;
    public String login(LoginForm loginForm) {
        // 1. 존재하는 회원인지 확인
        User findUser = userRepository.findByEmail(loginForm.getEmail())
                .orElseThrow(()-> new RuntimeException("아이디 또는 비밀번호를 확인해 주세요 - 아이디"));
        // 2. 비밀번호 체크
        if(!passwordEncoder.matches(loginForm.getPassword(), findUser.getPassword())) {
            throw new RuntimeException("아이디 또는 비밀번호를 확인해 주세요 - 비밀번호");
        }
        // 3. 토큰생성
        String token = jwtProvider.createToken(findUser.getEmail(), findUser.getId(), UserType.Answer);

        // 4. 토큰 레디스에 넣기
        redisClient.put(findUser.getId(), token);

        return "로그인 완료!";
    }

    public User join(JoinForm joinForm) {
        // 1. 가입돼있는 회원인지 확인
//        User findUser = userRepository.findByEmail(joinForm.getEmail())
//                .orElseThrow(()->new RuntimeException("이미 가입돼있는 회원입니다."));

        Optional<User> findUser = userRepository.findByEmail(joinForm.getEmail());
        if (findUser.isPresent()) {
            throw new RuntimeException("이미 존재하는 회원입니다.");
        }
        // 2. 저장
        User user = userRepository.save(
                User.builder()
                        .email(joinForm.getEmail())
                        .password(passwordEncoder.encode(joinForm.getPassword()))
                        .build());
        return user;
    }
}
