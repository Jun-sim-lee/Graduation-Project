package com.junsim.whereami.controller;

import com.junsim.whereami.dto.AuthTokenDTO;
import com.junsim.whereami.dto.LoginDTO;
import com.junsim.whereami.dto.SignUpDTO;
import com.junsim.whereami.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/signUp")
    public String signUp(@RequestBody SignUpDTO signUpDTO){
        authService.signUp(signUpDTO);
        return "ok";
    }

    @PostMapping("/login")
    public AuthTokenDTO login(@RequestBody LoginDTO loginDTO){
        AuthTokenDTO authTokenDTO = authService.login(loginDTO);
        return authTokenDTO;
    }
}
