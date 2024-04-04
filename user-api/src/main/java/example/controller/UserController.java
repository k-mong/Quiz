package example.controller;

import example.domain.entity.User;
import example.domain.model.JoinForm;
import example.domain.model.LoginForm;
import example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

//    @PostMapping("/test/login")
//    public String test() {
//        return "들어옴";
//    }

    @PostMapping("/login")
    public ResponseEntity<String>login (@ModelAttribute LoginForm loginForm) {
        String result = userService.login(loginForm);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/join")
    public ResponseEntity<User>join (@ModelAttribute JoinForm joinForm) {
        User result = userService.join(joinForm);
        return ResponseEntity.ok(result);
    }
}
