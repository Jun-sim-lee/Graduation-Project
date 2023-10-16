package com.junsim.whereami.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

@Getter
@AllArgsConstructor
public class RSSRequestDTO {
    private String uniqueRpiCode;
    private Integer x;
    private Integer y;
}
