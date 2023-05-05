package com.junsim.whereami.domain;

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
    @OneToMany(mappedBy = "resource")
    List<MemberResourceInterSection> memberList = new ArrayList<>();

    public Resource(String deviceName, Authority authority, Location location){
        this.deviceName = deviceName;
        this.authority = authority;
        this.location = location;
    }
}
