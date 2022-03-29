package com.znvks.salon.model.mapper;

import com.znvks.salon.model.dao.PetDAO;
import com.znvks.salon.model.dto.PetDTO;
import com.znvks.salon.model.entity.Pet;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring", uses = {AccountMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class PetMapper {

    @Autowired
    PetDAO petDAO;

    @Mapping(expression = "java(String.valueOf(pet.getKind().getKind()) )", target = "kind")
    public abstract PetDTO mapToDto(Pet pet);

    public abstract List<PetDTO> mapToListDto(List<Pet> pets);

    @Mapping(expression = "java((com.znvks.salon.model.entity.account.User) accountMapper.mapToEntity( petDTO.getUser()))", target = "user")
    @Mapping(expression = "java(com.znvks.salon.model.entity.Kind.builder().kind(petDTO.getKind()).build())", target = "kind")
    abstract Pet toEntity(PetDTO petDTO);

    public Pet mapToEntity(PetDTO petDTO) {
        Pet pet = toEntity(petDTO);
        if (Objects.nonNull(petDTO.getId())){
            pet.setId(petDTO.getId());
        }
        return pet;
    }
}
