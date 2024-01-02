package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/imageboard")
public class ImageBoardController {

    @GetMapping("/add")
    public void add(){
        log.info("GET /imageBoard/add");
    }
}
