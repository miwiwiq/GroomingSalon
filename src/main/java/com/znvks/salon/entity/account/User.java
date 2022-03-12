package com.znvks.salon.entity.account;

import com.znvks.salon.entity.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("user")
@PrimaryKeyJoinColumn(name = "account_id")
@Table(schema = "hibernate_demo")
public class User extends Account {

    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Pet> pets = new ArrayList<>();

    @Builder
    public User(String username, String password, String name, String surname, String phoneNumber, String email, List<Pet> pets) {
        super(username, password);
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.pets = pets;
    }

}
