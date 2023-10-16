package com.junsim.whereami.dto;

import com.junsim.whereami.domain.Resource;
import lombok.Getter;

@Getter
public class MyResourceResponseDTO {
    Long id;
    String deviceName;
    String isOn;

    public MyResourceResponseDTO(Resource resource) {
        this.id = resource.getId();
        this.deviceName = resource.getDeviceName();
        this.isOn = resource.getIsOn();
    }
}
