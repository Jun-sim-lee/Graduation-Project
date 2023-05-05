package com.junsim.whereami.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Greed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int x;
    int y;

    Ap ap1;
    Ap ap2;

    public Greed(int x, int y, Ap ap1, Ap ap2){
        this.x = x;
        this.y = y;
        this.ap1 = ap1;
        this.ap2 = ap2;

    }
}
