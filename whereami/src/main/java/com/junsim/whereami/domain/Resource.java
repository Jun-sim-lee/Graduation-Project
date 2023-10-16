package com.junsim.whereami.domain;

import com.junsim.whereami.dto.ChangeResourceRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String deviceName;
    @Enumerated(value = EnumType.STRING)
    Authority authority;
    Location location;
    String isOn;
    @OneToMany(mappedBy = "resource")
    List<MemberResourceInterSection> memberList = new ArrayList<>();

    public Resource(String deviceName, Authority authority, Location location){
        this.deviceName = deviceName;
        this.authority = authority;
        this.location = location;
        this.isOn = "";
    }

    public void addMember (MemberResourceInterSection memberResourceInterSection) {
        this.memberList.add(memberResourceInterSection);
    }

    public void turnOn(String email){
        this.isOn = email;
    }

    public void turnOff() {
        this.isOn = "";
    }

    public void changeInfo(ChangeResourceRequestDTO changeResourceRequestDTO) {
        if(changeResourceRequestDTO.getAuth().equals("학생"))
            this.authority = Authority.Student;
        if(changeResourceRequestDTO.getAuth().equals("교수"))
            this.authority = Authority.Professor;
    }
}
