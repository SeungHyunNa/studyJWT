package com.nas.blog.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.nas.blog.exception.ExceptionCode.*;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("entrypoint")
    public void entrypointException(){

    }
}
