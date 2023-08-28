package com.junsim.whereami.service;

import com.junsim.whereami.domain.Member;
import com.junsim.whereami.dto.AuthTokenDTO;
import com.junsim.whereami.dto.LoginDTO;
import com.junsim.whereami.dto.SignUpDTO;
import com.junsim.whereami.jwt.JwtTokenProvider;
import com.junsim.whereami.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.DeleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUpDTO signUpDTO){
        Member member = new Member(signUpDTO.getEmail(), passwordEncoder.encode(signUpDTO.getPassword()), signUpDTO.getNickName());
        memberRepository.save(member);
        System.out.println("email = " + member.getEmail());
        System.out.println("nickName = " + member.getNickName());
        System.out.println("password = " + member.getPassword());
    }

    public AuthTokenDTO login(LoginDTO loginDTO){
        Optional<Member> member = memberRepository.findByEmail(loginDTO.getEmail());
        System.out.println("member = " + member.get().getNickName());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
        AuthTokenDTO authTokenDto = tokenProvider.generateToken(authentication);

        return authTokenDto;

    }
}
