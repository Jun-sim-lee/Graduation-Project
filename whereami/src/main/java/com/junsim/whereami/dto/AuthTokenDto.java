package com.junsim.whereami.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class AuthTokenDto {
    private String grantType; // Bearer
    private String accessToken;

    @JsonFormat(timezone = "Asia/Seoul")
    private Date accessTokenValidTime;
}
