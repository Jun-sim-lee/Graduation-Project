package com.junsim.whereami.dto;

import com.junsim.whereami.domain.Ask;
import com.junsim.whereami.domain.Member;
import com.junsim.whereami.domain.Resource;
import lombok.Getter;

@Getter
public class AllAskResponseDTO {
    Long askId;
    Long resourceId;
    String deviceName;
    Integer x;
    Integer y;
    String nickName;
    String email;

    public AllAskResponseDTO(Ask ask) {
        this.askId = ask.getId();
        this.resourceId = ask.getResource().getId();
        this.deviceName = ask.getResource().getDeviceName();
        this.x = ask.getResource().getLocation().getX();
        this.y = ask.getResource().getLocation().getY();
        this.nickName = ask.getSender().getNickName();
        this.email = ask.getSender().getEmail();
    }
}
