package com.znvks.salon.dto;

import com.znvks.salon.entity.Form;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ReservationDTO {

    private Long id;
    private LocalDate date;
    private LocalTime time;
    private  double finalPrice;
    private  int rating;
    private FormDTO form;
}
