package com.junsim.whereami.controller;

import com.junsim.whereami.dto.*;
import com.junsim.whereami.errors.exception.Exception400;
import com.junsim.whereami.service.AuthService;
import com.junsim.whereami.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    private final MailService mailService;

    @GetMapping("/members")
    public List<AllMemberDTO> findAllMember(){
        return authService.findAllMember();
    }

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
        mailService.sendEmail(mailDTO.getEmail());

        return ResponseEntity.ok(true);
    }

    @GetMapping("/otp")
    public ResponseEntity<?> sendOTP() {
        String otp = mailService.sendOTP();
        return ResponseEntity.ok(otp);
    }

    @PostMapping("/checkOTP")
    public ResponseEntity<?> checkOTP(@RequestBody OtpDTO otpdto) {
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


    @PostMapping("/checkRpi")
    public ResponseEntity<?> checkRpi(@RequestBody RpiAuthRequestDTO rpiAuthRequestDTO) {
        authService.checkRpiCode(rpiAuthRequestDTO);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/checkRpiOn")
    public boolean checkRpiOn() {
        return authService.checkRpiOn();
    }

    @PostMapping("/admin/setRpi")
    public ResponseEntity<?> setRpiCode(@RequestBody RpiCodeRequestDTO rpiCodeRequestDTO) {
        authService.setRpiCode(rpiCodeRequestDTO);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/admin/setQr")
    public ResponseEntity<?> setQrCode(@RequestBody QrCodeRequestDTO qrCodeRequestDTO) {
        authService.setQrCode(qrCodeRequestDTO);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/admin/upgradeAuth")
    public ResponseEntity<?> upgradeAuth(@RequestBody AuthUpgradeRequestDTO authUpgradeRequestDTO) {
        authService.upgradeAuth(authUpgradeRequestDTO);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/checkQr")
    public ResponseEntity<?> checkQr(@RequestBody QrCheckRequestDTO qrCheckRequestDTO) {
        for (Integer pos : qrCheckRequestDTO.getPosition()) {
            System.out.println(pos);
        }
        authService.checkQrCode(qrCheckRequestDTO);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/admin/reset")
    public ResponseEntity<?> resetWrongCount(@RequestBody ResetWrongCountDTO resetWrongCountDTO) {
        authService.resetWrongCount(resetWrongCountDTO);
        return ResponseEntity.ok(true);
    }
    @PostMapping("/setToken")
    public ResponseEntity<?> setFCMToken(@RequestBody FCMTokenDTO fcmTokenDTO) {
        authService.setToken(fcmTokenDTO);
        return ResponseEntity.ok(true);
    }
    @PostMapping("/changeInfo")
    public ResponseEntity<?> changeInfo(@RequestBody ChangePasswordRequestDTO changePasswordRequestDTO) {
        authService.changePassword(changePasswordRequestDTO);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/checkPassword")
    public ResponseEntity<?> checkPassword(@RequestBody CheckPasswordDTO checkPasswordDTO) {
        authService.checkPassword(checkPasswordDTO);
        return ResponseEntity.ok(true);
    }

}
