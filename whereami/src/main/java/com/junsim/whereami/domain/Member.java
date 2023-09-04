package com.junsim.whereami.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "binary(16)")
    UUID id;
    
    String email;
    String password;
    String nickName;
    String rpiCode;
    String qrCode;
    boolean isLogin;
    LocalDateTime createAt;
    Integer wrongCount = 1;
    @Enumerated(value = EnumType.STRING)
    Authority authority;
    @OneToMany(mappedBy = "member")
    List<MemberResourceInterSection> myResource = new ArrayList<>();
    @OneToMany(mappedBy = "sender")
    List<Ask> myAskList = new ArrayList<>();

    public Member(String email, String password, String nickName){
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.authority = Authority.None;
        this.createAt = LocalDateTime.now();
        this.isLogin = false;
    }

    public void initializeWrongCount(){
        this.wrongCount = 0;
    }

}
