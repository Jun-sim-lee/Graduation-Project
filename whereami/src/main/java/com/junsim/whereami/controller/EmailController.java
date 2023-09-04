package com.junsim.whereami.controller;

import com.junsim.whereami.dto.MailDTO;
import com.junsim.whereami.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    @PostMapping("/send")
    public ResponseEntity<Object> send(@RequestBody MailDTO mailDTO) {

        emailService.sendEmail(mailDTO.getPNUEmail());

        // 결과 반환
        return ResponseEntity.ok(true);
    }
}
