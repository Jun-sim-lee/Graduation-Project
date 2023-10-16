package com.junsim.whereami.service;

import com.junsim.whereami.utility.RedisUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static com.junsim.whereami.utility.SecurityUtility.currentMember;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    private final RedisUtility redisUtility;


    public void sendEmail(String email) {
        Integer authNumber = makeEmailAuthNum();
        redisUtility.setValues(email, Integer.toString(authNumber), 300);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("부산대 인증 메일입니다!");
        simpleMailMessage.setText("인증 번호 입니다.\n"
                + authNumber +
                "\n잘 입력해 보세요!");
        // 이메일 발신
        javaMailSender.send(simpleMailMessage);
    }

    public String sendOTP() {
        String email = currentMember();
        String OTP = makeOTPNum();
        redisUtility.setValues(email, OTP, 60);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Wmi OTP입니다!");
        simpleMailMessage.setText("OTP 입니다.\n"
                + OTP +
                "\n잘 입력해 보세요!");
        // 이메일 발신
        javaMailSender.send(simpleMailMessage);

        return OTP;
    }

    public Integer makeEmailAuthNum() {
        return new Random().nextInt(888888) + 111111;
    }

    public String makeOTPNum() {
        String OTP = "";
        Random rd = new Random();
        String  initNum = Integer.toString(rd.nextInt(88888888) + 11111111);
        for(int i = 0; i < 4; i++) {
            Integer temp =rd.nextInt(8);
            OTP += initNum.charAt(temp);
        }
        return OTP;
    }
}
