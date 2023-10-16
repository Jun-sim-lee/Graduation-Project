package com.junsim.whereami.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.junsim.whereami.dto.MapResponseDTO;
import com.junsim.whereami.dto.PathResponseDTO;
import com.junsim.whereami.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class MapController {
    private final MapService mapService;

    @GetMapping("/myLocation")
    public MapResponseDTO myLocation() throws JsonProcessingException {
        return mapService.myLocation();
    }

    @GetMapping("/findPath")
    public PathResponseDTO findPath(@RequestParam int x, int y) {
        return mapService.findPath(new MapService.Pair(x,y));
    }
}
