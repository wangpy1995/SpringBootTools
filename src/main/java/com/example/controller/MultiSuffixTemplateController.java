package com.example.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/multi")
public class MultiSuffixTemplateController {

    @GetMapping("/html")
    public String home(){
        return "multi/index";
    }

    @GetMapping("/php")
    public String php(){
        return "multi/index.php";
    }
}
