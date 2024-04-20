package example.controller;

import example.domain.entity.Tester;
import example.domain.model.JoinForm;
import example.domain.model.LoginForm;
import example.service.TesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tester")
public class TesterController {
    private final TesterService testerService;

    @Value("${quiz.port}")
    private String quizPort;

    @PostMapping("/login")
    public ModelAndView login (@ModelAttribute LoginForm loginForm) {
        String result = testerService.login(loginForm);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("quizPort", quizPort);
        modelAndView.addObject("userEmail",loginForm.getEmail());
        return modelAndView;
    }

//    @PostMapping("/join")
//    public ResponseEntity<Tester> join (@ModelAttribute JoinForm joinForm) {
//        Tester result = testerService.join(joinForm);
//        return ResponseEntity.ok(result);
//    }

    @PostMapping("/join")
    public ModelAndView join (@ModelAttribute JoinForm joinForm) {
        Tester result = testerService.join(joinForm);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
