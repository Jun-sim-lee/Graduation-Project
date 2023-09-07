package com.junsim.whereami.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class QrCheckRequestDTO {
    List<Integer> position;
    String number;
}
