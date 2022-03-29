package com.znvks.salon.model.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<DTO> {

    Optional<DTO> getById(Long id);

    Long save(DTO entity);

    void update(DTO entity);

    void delete(DTO entity);

    List<DTO> getAll();

}
