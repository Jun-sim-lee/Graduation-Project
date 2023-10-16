package com.junsim.whereami.dto;

import lombok.Getter;

@Getter
public class AcceptAskRequestDTO {
    Long askId;
    String email;
    Long deviceId;
}
