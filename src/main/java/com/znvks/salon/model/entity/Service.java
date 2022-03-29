package com.znvks.salon.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(schema = "hibernate_demo")
public class Service extends BaseEntity<Long> {

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "duration", nullable = false)
    private int duration;
    @Column(name = "price", nullable = false)
    private double price;

}
