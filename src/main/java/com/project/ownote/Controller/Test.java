package com.project.ownote.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Test {

    @GetMapping("/dd/test")
    public String test() {
        return "/dd/test";
    }
}
