package com.junsim.whereami.dto;

import com.junsim.whereami.service.MapService;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class PathResponseDTO {
    ArrayList<MapService.Pair> shortestPath;

    public PathResponseDTO(ArrayList<MapService.Pair> path) {
        this.shortestPath = path;
    }
}
