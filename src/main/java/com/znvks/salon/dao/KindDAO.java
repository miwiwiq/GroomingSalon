package com.znvks.salon.dao;

import com.znvks.salon.entity.Kind;
import com.znvks.salon.entity.Reservation;

import java.util.Optional;

public interface KindDAO extends BaseDAO<Long, Kind> {

    Optional<Kind> getByName(String name);
}
