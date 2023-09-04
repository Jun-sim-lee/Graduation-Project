package com.junsim.whereami.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    public void sendEmail(String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("뭐더라!");
        simpleMailMessage.setText("인증 번호 입니다.\n"
                + makeNum() +
                "\n잘 입력해 보세요!");

        // 이메일 발신
        javaMailSender.send(simpleMailMessage);
    }

    public Integer makeNum() {
        return new Random().nextInt(888888) + 111111;
    }
}
