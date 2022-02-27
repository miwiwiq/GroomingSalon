package com.znvks.salon.entity.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@DiscriminatorValue("admin")
@Table(schema = "hibernate_demo")
@PrimaryKeyJoinColumn(name = "account_id")
public class Admin extends Account {

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private Level level;

    @Builder
    public Admin(String username, String password, Level level) {
        super(username, password);
        this.level=level;
    }

}
