package example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/user")
public class UserViewController {

    @GetMapping("/login")
    public String showLogin() {
        return "user/login";
    }

    @GetMapping("/join")
    public String showJoin() {
        return "user/join";
    }
}
