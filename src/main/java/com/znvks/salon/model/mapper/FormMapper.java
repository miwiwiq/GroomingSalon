package com.znvks.salon.model.mapper;

import com.znvks.salon.model.dao.FormDAO;
import com.znvks.salon.model.dto.FormDTO;
import com.znvks.salon.model.entity.Form;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

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
        if (Objects.nonNull( formDTO.getId())){
            form.setId(formDTO.getId());
        }
        return form;
    }
}
