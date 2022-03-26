package com.znvks.salon.dto;

import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.account.Level;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AccountDTO implements Serializable {

    private Long id;
    private String username;
    private String password;
    private String role;
    private Level level;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private List<Pet> pets;
}
