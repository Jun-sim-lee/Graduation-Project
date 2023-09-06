package com.junsim.whereami.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.junsim.whereami.domain.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class AuthTokenDTO {
    private String grantType; // Bearer
    private String accessToken;

    @JsonFormat(timezone = "Asia/Seoul")
    private Date accessTokenValidTime;

}
