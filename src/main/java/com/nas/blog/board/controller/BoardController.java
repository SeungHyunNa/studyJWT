package com.nas.blog.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/board/saveForm")
    public String home() {
        return "board/saveForm";
    }
}
