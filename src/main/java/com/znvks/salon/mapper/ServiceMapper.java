package com.znvks.salon.mapper;

import com.znvks.salon.dao.ServiceDAO;
import com.znvks.salon.dto.AccountDTO;
import com.znvks.salon.dto.PetDTO;
import com.znvks.salon.dto.ServiceDTO;
import com.znvks.salon.entity.BaseEntity;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.Service;
import com.znvks.salon.entity.account.Admin;
import com.znvks.salon.service.ServiceService;
import org.hibernate.Session;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ServiceMapper {

    public abstract ServiceDTO mapToDto(Service service);

    public abstract List<ServiceDTO> mapToListDto(List<Service> services);

    abstract Service toEntity(ServiceDTO serviceDTO);

    public Service mapToEntity(ServiceDTO serviceDTO) {
        Service service = toEntity(serviceDTO);
        service.setId(serviceDTO.getId());
        return service;
    }

    public List<Service> mapToListEntity(List<ServiceDTO> services){
        List<Service> list = new ArrayList<Service>( services.size() );
        for ( ServiceDTO s : services ) {
            list.add( mapToEntity( s ) );
        }
        return list;
    }
}
