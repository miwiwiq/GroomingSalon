package com.znvks.salon.model.service;

import com.znvks.salon.model.dto.AccountDTO;
import com.znvks.salon.model.dto.FormDTO;
import com.znvks.salon.model.dto.ReservationDTO;

import java.util.List;
import java.util.Optional;

public interface ReservationService extends BaseService<ReservationDTO>{

    List<ReservationDTO> getOrdersByAcc(AccountDTO account);

    Optional<ReservationDTO> getOrdersByForm(FormDTO form);
}
