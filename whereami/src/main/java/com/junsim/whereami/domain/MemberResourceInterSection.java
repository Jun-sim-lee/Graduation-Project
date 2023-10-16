package com.junsim.whereami.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class MemberResourceInterSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    Member member;
    @ManyToOne
    @JoinColumn(name = "RESOURCE_ID")
    Resource resource;

    public MemberResourceInterSection(Member member, Resource resource){
        this.member = member;
        this.resource = resource;
    }
}
