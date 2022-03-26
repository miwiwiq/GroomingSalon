package com.znvks.salon.mapper;

import com.znvks.salon.dao.PetDAO;
import com.znvks.salon.dto.AccountDTO;
import com.znvks.salon.dto.FormDTO;
import com.znvks.salon.dto.PetDTO;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.*;
import com.znvks.salon.entity.account.Admin;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AccountMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class PetMapper {

    @Autowired
    PetDAO petDAO;

    @Mapping(expression = "java(String.valueOf(pet.getKind().getKind()) )", target = "kind")
    public abstract PetDTO mapToDto(Pet pet);

    public abstract List<PetDTO> mapToListDto(List<Pet> pets);

    @Mapping(expression = "java((com.znvks.salon.entity.account.User) accountMapper.mapToEntity( petDTO.getUser()))", target = "user")
    @Mapping(expression = "java(com.znvks.salon.entity.Kind.builder().kind(petDTO.getKind()).build())", target = "kind")
    abstract Pet toEntity(PetDTO petDTO);

    public Pet mapToEntity(PetDTO petDTO) {
        Pet pet = toEntity(petDTO);
        pet.setId(petDTO.getId());
        return pet;
    }
}
