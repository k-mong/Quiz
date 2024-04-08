package com.mong.quiz.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/tester")
public class TesterViewController {

    @GetMapping("/list")
    public String showList() {
        return "tester/list";
    }
}
