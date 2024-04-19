package example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/tester")
public class TesterViewController {

    @GetMapping("/login")
    public String showLogin() {
        return "tester/login";
    }

    @GetMapping("/join")
    public String showJoin() {
        return "tester/join";
    }
}
