package example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class IndexViewController {
    @Value("${quiz.port}")
    private String quizPort;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("quizPort", quizPort);
        return "index";
    }
}
