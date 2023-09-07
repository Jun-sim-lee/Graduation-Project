package com.junsim.whereami.dto;

import com.junsim.whereami.domain.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponseDTO {
    String username;
    AuthTokenDTO authTokenDTO;
    Authority authority;
}
