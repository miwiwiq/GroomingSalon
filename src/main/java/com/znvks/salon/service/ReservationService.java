package com.znvks.salon.service;

import com.znvks.salon.dto.FormDTO;
import com.znvks.salon.dto.PetDTO;
import com.znvks.salon.dto.ReservationDTO;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.Reservation;
import com.znvks.salon.entity.account.Account;

import java.util.List;
import java.util.Optional;

public interface ReservationService extends BaseService<ReservationDTO, Reservation>{

    List<ReservationDTO> getOrdersByAcc(Account account);

    Optional<ReservationDTO> getOrdersByForm(Form form);
}