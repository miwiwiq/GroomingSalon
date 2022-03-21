package com.znvks.salon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PetDTO implements Serializable {

    private Long id;
    private String name;
    private String kind;
    @ToString.Exclude
    private AccountDTO account;
}
