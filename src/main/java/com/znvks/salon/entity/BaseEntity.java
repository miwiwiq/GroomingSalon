package com.znvks.salon.entity;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;
import java.io.Serializable;

@Getter
@MappedSuperclass
public abstract class BaseEntity<PK extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "table_gen")
    @TableGenerator(
            name = "table_gen",
            table = "table_gen_id",
            schema = "hibernate_demo",
            pkColumnName = "table_name",
            valueColumnName = "table_id",
            allocationSize = 1
    )
    protected PK id;
}
