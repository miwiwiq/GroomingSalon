package com.znvks.salon.mapper;

import com.znvks.salon.dao.ReservationDAO;
import com.znvks.salon.dto.FormDTO;
import com.znvks.salon.dto.PetDTO;
import com.znvks.salon.dto.ReservationDTO;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.Reservation;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = {FormMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ReservationMapper {

    @Autowired
    ReservationDAO reservationDAO;

    @Mapping(source = "date", target = "date", dateFormat = "dd.MM.yyyy")
    public  abstract ReservationDTO mapToDto(Reservation reservation);

    public abstract List<ReservationDTO> mapToListDto(List<Reservation> reservations);

    @Mapping(expression = "java(formMapper.mapToEntity(reservationDTO.getForm()))", target = "form")
    abstract Reservation toEntity(ReservationDTO reservationDTO);

    public Reservation mapToEntity(ReservationDTO reservationDTO) {
        Reservation reservation = toEntity(reservationDTO);
        reservation.setId(reservationDTO.getId());
        return reservation;
    }

}
