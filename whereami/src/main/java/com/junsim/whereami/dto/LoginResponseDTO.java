package com.junsim.whereami.dto;

import com.junsim.whereami.domain.Authority;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginResponseDTO {
    AuthTokenDTO authTokenDTO;
    Authority authority;
}
