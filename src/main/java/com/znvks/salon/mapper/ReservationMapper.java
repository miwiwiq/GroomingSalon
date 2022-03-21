package com.znvks.salon.mapper;

import com.znvks.salon.dto.FormDTO;
import com.znvks.salon.dto.ReservationDTO;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Reservation;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {FormMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReservationMapper {

    @Mapping(source = "date", target = "date", dateFormat = "dd.MM.yyyy")
    ReservationDTO mapToDto(Reservation reservation);

    List<ReservationDTO> mapToListDto(List<Reservation> reservations);

}
