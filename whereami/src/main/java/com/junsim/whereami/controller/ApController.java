package com.junsim.whereami.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.junsim.whereami.service.ApService;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class ApController {
    private final ApService apService;

    @PostMapping("/ap")
    public void fingerPrint(@RequestBody HashMap<String ,Integer> rss){
        apService.fingerprint(rss);

    }
}
