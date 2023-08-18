package com.junsim.whereami.service;

import com.junsim.whereami.domain.Member;
import com.junsim.whereami.dto.LoginDTO;
import com.junsim.whereami.dto.SignUpDTO;
import com.junsim.whereami.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    public void signUp(SignUpDTO signUpDTO){
        Member member = new Member(signUpDTO.getEmail(), signUpDTO.getPassword(), signUpDTO.getNickName());
        memberRepository.save(member);
        System.out.println("email = " + member.getEmail());
        System.out.println("nickName = " + member.getNickName());
        System.out.println("password = " + member.getPassword());
    }

    public void logIn(LoginDTO loginDTO){

    }
}
