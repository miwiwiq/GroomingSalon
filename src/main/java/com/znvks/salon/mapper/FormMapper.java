package com.znvks.salon.mapper;

import com.znvks.salon.dao.FormDAO;
import com.znvks.salon.dto.AccountDTO;
import com.znvks.salon.dto.FormDTO;
import com.znvks.salon.dto.PetDTO;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.Admin;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PetMapper.class, ServiceMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class FormMapper {

    @Autowired
    FormDAO formDAO;

    public abstract FormDTO mapToDto(Form form);

    public abstract List<FormDTO> mapToListDto(List<Form> forms);

    @Mapping(expression = "java(petMapper.mapToEntity(formDTO.getPet()))", target = "pet")
    @Mapping(expression = "java(serviceMapper.mapToListEntity(formDTO.getServices()))", target = "services")
    abstract Form toEntity(FormDTO formDTO);

    public Form mapToEntity(FormDTO formDTO) {
        Form form = toEntity(formDTO);
        form.setId(formDTO.getId());
        return form;
    }
}
