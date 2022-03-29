package com.znvks.salon.model.mapper;

import com.znvks.salon.model.dto.ServiceDTO;
import com.znvks.salon.model.entity.Service;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ServiceMapper {

    public abstract ServiceDTO mapToDto(Service service);

    public abstract List<ServiceDTO> mapToListDto(List<Service> services);

    abstract Service toEntity(ServiceDTO serviceDTO);

    public Service mapToEntity(ServiceDTO serviceDTO) {
        Service service = toEntity(serviceDTO);
        if (Objects.nonNull(serviceDTO.getId())){
            service.setId(serviceDTO.getId());
        }
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
