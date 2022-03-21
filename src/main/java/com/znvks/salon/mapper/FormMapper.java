package com.znvks.salon.mapper;

import com.znvks.salon.dto.FormDTO;
import com.znvks.salon.dto.PetDTO;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Pet;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PetMapper.class, ServiceMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FormMapper {

    FormDTO mapToDto(Form form);

    List<FormDTO> mapToListDto(List<Form> forms);
}
