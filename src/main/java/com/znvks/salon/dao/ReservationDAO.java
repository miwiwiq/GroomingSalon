package com.znvks.salon.dao;

import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Reservation;
import com.znvks.salon.entity.account.Account;

import java.util.List;
import java.util.Optional;

public interface ReservationDAO extends BaseDAO<Long, Reservation> {

    List<Reservation> getOrdersByAcc(Account account);

    Optional<Reservation> getOrdersByForm(Form form);
}
