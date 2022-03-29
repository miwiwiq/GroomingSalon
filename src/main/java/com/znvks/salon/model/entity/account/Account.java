package com.znvks.salon.model.entity.account;

import com.znvks.salon.model.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role")
@Table(schema = "hibernate_demo")
public abstract class Account extends BaseEntity<Long> {

    @Column(unique = true, nullable = false)
    protected String username;
    @Column(nullable = false)
    protected String password;
    @Column(name = "role", insertable = false, updatable = false)
    protected String role;

}
