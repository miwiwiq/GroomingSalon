package com.znvks.salon.model.dao;

import com.znvks.salon.model.entity.Kind;

import java.util.Optional;

public interface KindDAO extends BaseDAO<Long, Kind> {

    Optional<Kind> getByName(String name);
}
