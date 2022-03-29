package com.znvks.salon.model.dao;

import com.znvks.salon.model.entity.Form;
import com.znvks.salon.model.entity.Reservation;
import com.znvks.salon.model.entity.account.Account;

import java.util.List;
import java.util.Optional;

public interface ReservationDAO extends BaseDAO<Long, Reservation> {

    List<Reservation> getOrdersByAcc(Account account);

    Optional<Reservation> getOrdersByForm(Form form);
}
