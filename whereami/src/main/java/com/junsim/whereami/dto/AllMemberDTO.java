package com.junsim.whereami.dto;

import com.junsim.whereami.domain.Authority;
import com.junsim.whereami.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AllMemberDTO {
    String nickName;
    String email;
    String authority;

    public AllMemberDTO(Member member) {
        this.nickName = member.getNickName();
        this.email = member.getEmail();
        this.authority = member.getAuthority().toString();
    }
}
