package com.junsim.whereami.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Location {
    Integer x;
    Integer y;
}
