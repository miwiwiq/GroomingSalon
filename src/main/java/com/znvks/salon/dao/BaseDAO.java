package com.znvks.salon.dao;

import com.znvks.salon.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseDAO<PK extends Serializable, E extends BaseEntity<PK>> {

    Optional<E> getById(PK id);

    PK save(E entity);

    void update(E entity);

    void delete(E entity);

    List<E> getAll();

}
