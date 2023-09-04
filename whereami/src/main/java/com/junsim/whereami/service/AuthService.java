package com.junsim.whereami.service;

import com.junsim.whereami.domain.Member;
import com.junsim.whereami.dto.AuthTokenDTO;
import com.junsim.whereami.dto.LoginDTO;
import com.junsim.whereami.dto.SignUpDTO;
import com.junsim.whereami.errors.exception.Exception400;
import com.junsim.whereami.errors.exception.Exception404;
import com.junsim.whereami.jwt.JwtTokenProvider;
import com.junsim.whereami.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final EntityManager em;

    public void signUp(SignUpDTO signUpDTO){
        if(memberRepository.findByEmail(signUpDTO.getEmail()).isPresent())
            throw new Exception400("이미 존재하는 이메일입니다.");
        Member member = new Member(signUpDTO.getEmail(), passwordEncoder.encode(signUpDTO.getPassword()), signUpDTO.getNickName());
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public AuthTokenDTO login(LoginDTO loginDTO){
        Optional<Member> loginMember = memberRepository.findByEmail(loginDTO.getEmail());
        if(loginMember.isEmpty())
            throw new Exception404("존재하지 않는 이메일입니다.");
        if(loginMember.get().getWrongCount() > 5)
            throw new Exception400("기능이 제한된 계정입니다.");
        if(!passwordEncoder.matches(loginDTO.getPassword(), loginMember.get().getPassword())){
            throw new Exception400("비밀번호가 틀렸습니다.");
        }
        loginMember.get().initializeWrongCount();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
        AuthTokenDTO authTokenDto = tokenProvider.generateToken(authentication);

        return authTokenDto;
    }

    public void testLogin() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

    }

}
