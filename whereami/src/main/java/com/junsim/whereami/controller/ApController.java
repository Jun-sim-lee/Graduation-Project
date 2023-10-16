package com.junsim.whereami.controller;

import com.junsim.whereami.domain.Location;
import com.junsim.whereami.dto.RSSRequestDTO;
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
    public void fingerPrint(@RequestBody RSSRequestDTO rssRequestDTO){
        System.out.println(rssRequestDTO.getX() + " " + rssRequestDTO.getY());
        apService.saveLocation(new Location(rssRequestDTO.getX(), rssRequestDTO.getY()), rssRequestDTO.getUniqueRpiCode());
        // 어디 서비스 가서 RSSRequestDto.getUniqueRpiCode 해서
        // 유저 찾아서 위치 저장
    }

}
