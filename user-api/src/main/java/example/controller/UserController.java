package example.controller;

import example.domain.entity.User;
import example.domain.model.JoinForm;
import example.domain.model.LoginForm;
import example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Value("${quiz.port}")
    private String quizPort;

//    @PostMapping("/test/login")
//    public String test() {
//        return "들어옴";
//    }

    @PostMapping("/login")
    public ModelAndView login (@ModelAttribute LoginForm loginForm) {
        String result = userService.login(loginForm);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("quizPort",quizPort);
        modelAndView.addObject("userEmail",loginForm.getEmail());
        return modelAndView;
    }

    @PostMapping("/join")
    public ResponseEntity<User>join (@ModelAttribute JoinForm joinForm) {
        User result = userService.join(joinForm);
        return ResponseEntity.ok(result);
    }

}
