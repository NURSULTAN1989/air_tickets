package com.air_tickets.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "aircrafts")
public class Aircrafts {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "model")
    private String model;
    @Column(name = "econom_capacity")
    private int econom_capacity;
    @Column(name = "business_capacity")
    private int business_capacity;
}
