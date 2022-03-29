package com.znvks.salon.model.mapper;

import com.znvks.salon.model.dao.ReservationDAO;
import com.znvks.salon.model.dto.ReservationDTO;
import com.znvks.salon.model.entity.Reservation;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring", uses = {FormMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ReservationMapper {

    @Autowired
    ReservationDAO reservationDAO;

    @Mapping(source = "date", target = "date", dateFormat = "dd.MM.yyyy")
    public abstract ReservationDTO mapToDto(Reservation reservation);

    public abstract List<ReservationDTO> mapToListDto(List<Reservation> reservations);

    @Mapping(expression = "java(formMapper.mapToEntity(reservationDTO.getForm()))", target = "form")
    abstract Reservation toEntity(ReservationDTO reservationDTO);

    public Reservation mapToEntity(ReservationDTO reservationDTO) {
        Reservation reservation = toEntity(reservationDTO);
        if (Objects.nonNull(reservationDTO.getId())) {
            reservation.setId(reservationDTO.getId());
        }
        return reservation;
    }

}
