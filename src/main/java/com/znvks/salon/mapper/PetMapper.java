package com.znvks.salon.mapper;

import com.znvks.salon.dto.AccountDTO;
import com.znvks.salon.dto.PetDTO;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.account.Admin;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AccountMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PetMapper {

    @Mapping(expression = "java(String.valueOf(pet.getKind().getKind()) )", target = "kind")
    PetDTO mapToDto(Pet pet);

    List<PetDTO> mapToListDto(List<Pet> pets);

//    @Mapping(expression = "java(String.valueOf(pet.getKind().getKind()) )", source = "kind", target = "java()")
//    Pet mapToEntity(PetDTO petDTO);
//
//    List<Pet> mapToListEntity(List<PetDTO> petDTOs);
}
