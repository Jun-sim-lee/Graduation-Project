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
    @Column(unique = true)
    String email;
    String password;
    String nickName;
    String rpiCode;
    String qrCode;
    boolean rpiStatus;
    LocalDateTime createAt;
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
        this.rpiStatus = false;
    }

    public void upgrade() {
        this.authority = Authority.Student;
    }
    public void setRpiCode(String code) {
        this.rpiCode = code;
    }
    public void rpiOff(){
        this.rpiStatus = false;
    }
    public void rpiOn(){
        this.rpiStatus = true;
    }
    public boolean rpiStatus() {
        return this.rpiStatus;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public void upgradeAuth(String auth) {
        if(auth.equals("Student"))
            this.authority = Authority.Student;
        else if (auth.equals("Professor")) {
            this.authority = Authority.Professor;
        }
    }
}
