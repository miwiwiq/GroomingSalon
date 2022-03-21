package com.znvks.salon.service.impl;

import com.znvks.salon.dao.FormDAO;
import com.znvks.salon.dao.ReservationDAO;
import com.znvks.salon.dto.ReservationDTO;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.Reservation;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.mapper.FormMapper;
import com.znvks.salon.mapper.ReservationMapper;
import com.znvks.salon.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDAO reservationDAO;
    private final ReservationMapper reservationMapper;

    @Override
    public Optional<ReservationDTO> getById(Long id) {
        Optional<Reservation> reservation = reservationDAO.getById(id);
        return Optional.ofNullable(reservationMapper.mapToDto(reservation.orElse(null)));
    }

    @Override
    public Long save(Reservation reservation) {
        return reservationDAO.save(reservation);
    }

    @Override
    public void update(Reservation reservation) {
        reservationDAO.update(reservation);
    }

    @Override
    public void delete(Reservation reservation) {
        reservationDAO.delete(reservation);
    }

    @Override
    public List<ReservationDTO> getAll() {
        List<Reservation> reservations = reservationDAO.getAll();
        return reservationMapper.mapToListDto(reservations);
    }

    @Override
    public List<ReservationDTO> getOrdersByAcc(Account account) {
        List<Reservation> reservations = reservationDAO.getOrdersByAcc(account);
        return reservationMapper.mapToListDto(reservations);
    }

    @Override
    public Optional<ReservationDTO> getOrdersByForm(Form form) {
        Optional<Reservation> reservation = reservationDAO.getOrdersByForm(form);
        return Optional.ofNullable(reservationMapper.mapToDto(reservation.orElse(null)));
    }
}
