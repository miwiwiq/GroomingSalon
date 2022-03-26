package com.znvks.salon.service;

import com.znvks.salon.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService<DTO> {

    Optional<DTO> getById(Long id);

    Long save(DTO entity);

    void update(DTO entity);

    void delete(DTO entity);

    List<DTO> getAll();

}
