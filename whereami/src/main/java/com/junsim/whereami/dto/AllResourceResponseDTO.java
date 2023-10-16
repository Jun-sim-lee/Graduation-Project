package com.junsim.whereami.dto;

import com.junsim.whereami.domain.Location;
import com.junsim.whereami.domain.Resource;
import lombok.Getter;

@Getter
public class AllResourceResponseDTO {
    Long id;
    String deviceName;
    String auth;
    String isOn;
    Location location;

    public AllResourceResponseDTO(Resource resource) {
        this.id = resource.getId();
        this.deviceName = resource.getDeviceName();
        this.auth = resource.getAuthority().toString();
        this.isOn = resource.getIsOn();
        this.location = resource.getLocation();
    }
}
