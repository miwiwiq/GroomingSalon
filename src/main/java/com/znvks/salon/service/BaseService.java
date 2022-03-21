package com.znvks.salon.service;

import com.znvks.salon.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService<DTO, E> {

    Optional<DTO> getById(Long id);

    Long save(E entity);

    void update(E entity);

    void delete(E entity);

    List<DTO> getAll();

}
