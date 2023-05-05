package com.junsim.whereami.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApController {

    @PostMapping("/test")
    public void Test(@RequestBody String rss){
        System.out.println("rss = " + rss);
    }
}
