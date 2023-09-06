package com.junsim.whereami.controller;

import com.junsim.whereami.dto.*;
import com.junsim.whereami.errors.exception.Exception400;
import com.junsim.whereami.repository.MemberRepository;
import com.junsim.whereami.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MemberRepository memberRepository;
    @PostMapping("/signUp")
    public String signUp(@RequestBody SignUpDTO signUpDTO){
        authService.signUp(signUpDTO);
        return "ok";
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO loginDTO){
        LoginResponseDTO loginResponseDTO = authService.login(loginDTO);
        return loginResponseDTO;
    }
    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestBody MailDTO mailDTO) {
        System.out.println("mailDTO.getPNUEmail() = " + mailDTO.getEmail());
        authService.sendEmail(mailDTO.getEmail());

        return ResponseEntity.ok(true);
    }

    @GetMapping("/otp")
    public ResponseEntity<?> sendOTP() {
        String otp = authService.sendOTP();
        return ResponseEntity.ok(otp);
    }

    @PostMapping("/checkOTP")
    public ResponseEntity<?> checkOTP(@RequestBody OTPDTO otpdto) {
        if(authService.checkOTP(otpdto))
            return ResponseEntity.ok(true);
        else
            throw new Exception400("OTP가 틀려부러쓰");

    }

    @PostMapping("/emailAuth")
    public ResponseEntity<?> emailAuth(@RequestBody EmailAuthDTO emailAuthDTO) {
        authService.emailAuth(emailAuthDTO);
        return ResponseEntity.ok(true);
    }

}
