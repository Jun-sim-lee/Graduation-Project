package com.junsim.whereami.service;

import com.junsim.whereami.domain.Member;
import com.junsim.whereami.dto.*;
import com.junsim.whereami.errors.exception.Exception400;
import com.junsim.whereami.errors.exception.Exception404;
import com.junsim.whereami.jwt.JwtTokenProvider;
import com.junsim.whereami.repository.MemberRepository;
import com.junsim.whereami.utility.RedisUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.junsim.whereami.utility.SecurityUtility.currentMember;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtility redisUtility;

    public List<AllMemberDTO> findAllMember() {
        List<Member> memberList = memberRepository.findAll();
        return memberList.stream()
                .map(AllMemberDTO::new)
                .collect(Collectors.toList());
    }

    public void changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {
        Member member = memberRepository.findByEmail(currentMember()).get();
        member.changeInfo(changePasswordRequestDTO.getNickName(),
                passwordEncoder.encode(changePasswordRequestDTO.getPassword()));

    }

    public void signUp(SignUpDTO signUpDTO){
        if(memberRepository.findByEmail(signUpDTO.getEmail()).isPresent())
            throw new Exception400("이미 존재하는 이메일입니다.");
        Member member = new Member(signUpDTO.getEmail(), passwordEncoder.encode(signUpDTO.getPassword()), signUpDTO.getNickName());
        memberRepository.save(member);
    }

    public LoginResponseDTO login(LoginDTO loginDTO){
        Optional<Member> loginMember = memberRepository.findByEmail(loginDTO.getEmail());
        String wrongCount = redisUtility.getValues(loginDTO.getEmail());
        if(loginMember.isEmpty())
            throw new Exception404("존재하지 않는 이메일입니다.");
        if(wrongCount != null && wrongCount.length() > 4)
            throw new Exception400("기능이 제한된 계정입니다.");
        if(!passwordEncoder.matches(loginDTO.getPassword(), loginMember.get().getPassword())){
            checkWrongPassword(loginDTO.getEmail());
            throw new Exception400("비밀번호가 " + redisUtility.getValues(loginDTO.getEmail()).length() + "회 틀렸습니다.");
        }
        redisUtility.deleteValues(loginDTO.getEmail());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
        AuthTokenDTO authTokenDto = tokenProvider.generateToken(authentication);
        loginMember.get().rpiOff();
        return new LoginResponseDTO(loginMember.get().getNickName(), authTokenDto, loginMember.get().getAuthority());
    }

    public void emailAuth(EmailAuthDTO emailAuthDTO) {

        if(redisUtility.getValues(emailAuthDTO.getEmail()).equals(emailAuthDTO.getAuthNum())){
            memberRepository.findByEmail(currentMember()).get().upgrade();
            redisUtility.deleteValues(emailAuthDTO.getEmail());
        }
        else
            throw new Exception400("인증번호가 틀려부러쓰");
    }

    public boolean checkOTP(OtpDTO otpdto){
        if(redisUtility.getValues(currentMember()).equals(otpdto.getOtp())){
            redisUtility.deleteValues(currentMember());
            return true;
        }
        else
            return false;
    }

    public void checkWrongPassword(String email) {
        if(redisUtility.getValues(email) == null)
            redisUtility.setValues(email, "1");
        else
            redisUtility.setValues(email, redisUtility.getValues(email) + "1");
    }

    public void setRpiCode(RpiCodeRequestDTO rpiCodeRequestDTO) {
        Optional<Member> member = memberRepository.findByEmail(rpiCodeRequestDTO.getEmail());
        member.get().setRpiCode(rpiCodeRequestDTO.getCode());
    }

    public void checkRpiCode(RpiAuthRequestDTO rpiAuthRequestDTo) {
        Optional<Member> member = memberRepository.findByRpi(rpiAuthRequestDTo.getCode());
        if(!member.isPresent())
            throw new Exception400("없는 Rpi여");
        else
            member.get().rpiOn();

    }
    public boolean checkRpiOn(){
        return memberRepository.findByEmail(currentMember()).get().rpiStatus();
    }

    public void setQrCode(QrCodeRequestDTO qrCodeRequestDTO) {
        Optional<Member> member = memberRepository.findByEmail(qrCodeRequestDTO.getEmail());
        if(!member.isPresent())
            throw new Exception400("없는 email입니다.");
        else
            member.get().setQrCode(qrCodeRequestDTO.getQrCode());
    }

    public void checkQrCode(QrCheckRequestDTO qrCheckRequestDTO) {
        Member member = memberRepository.findByEmail(currentMember()).get();
        String number = qrCheckRequestDTO.getNumber();
        for(int i = 0; i < 4; i++) {
            if(member.getQrCode().charAt(qrCheckRequestDTO.getPosition().get(i) - 1) != number.charAt(i))
                throw new Exception400("Qr코드가 틀려부러쓰");
        }
    }

    public void upgradeAuth(AuthUpgradeRequestDTO authUpgradeRequestDTO) {
        Optional<Member> member = memberRepository.findByEmail(authUpgradeRequestDTO.getEmail());
        if(member.isEmpty())
            throw new Exception400("없는 email입니다.");
        else
            member.get().upgradeAuth(authUpgradeRequestDTO.getAuth());
    }

    public void resetWrongCount(ResetWrongCountDTO resetWrongCountDTO) {
        if(redisUtility.getValues(resetWrongCountDTO.getEmail()) == null)
            throw new Exception400("없는 email입니다.");
        redisUtility.deleteValues(resetWrongCountDTO.getEmail());
    }


    public void setToken(FCMTokenDTO fcmTokenDTO) {
        Optional<Member> member = memberRepository.findByEmail(currentMember());
        if(member.isEmpty())
            throw new Exception400("없는 email입니다.");
        else
            member.get().setToken(fcmTokenDTO.getToken());
    }

    public void checkPassword(CheckPasswordDTO checkPasswordDTO) {
        Optional<Member> member = memberRepository.findByEmail(currentMember());
        if(member.isPresent()){
            if(!passwordEncoder.matches(checkPasswordDTO.getPassword(), member.get().getPassword()))
                throw new Exception400("비밀번호가 틀려부러쓰");
        }
        else
            throw new Exception400("없는 사람이여");
    }
}
