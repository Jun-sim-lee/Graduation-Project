package com.junsim.whereami.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Ask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "SENDER_ID")
    Member sender;
    @OneToOne
    Resource resource;

    public Ask(Member member, Resource resource){
        this.sender = member;
        this.resource = resource;
    }
}
