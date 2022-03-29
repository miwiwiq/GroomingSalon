package com.znvks.salon.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(schema = "hibernate_demo")
public class Reservation extends BaseEntity<Long> {

    @Column(name = "date")
    private LocalDate date;
    @Column(name = "time")
    private LocalTime time;
    @Column(name = "price")
    private  double finalPrice;
    @Column(name = "rating")
    private  int rating;
    @OneToOne(fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JoinColumn(name = "form_id")
    private Form form;
}
