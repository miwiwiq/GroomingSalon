package com.znvks.salon.mapper;

import com.znvks.salon.dao.ServiceDAO;
import com.znvks.salon.dto.ServiceDTO;
import com.znvks.salon.entity.BaseEntity;
import com.znvks.salon.entity.Service;
import com.znvks.salon.service.ServiceService;
import org.hibernate.Session;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ServiceMapper {

    ServiceDTO mapToDto(Service service);

    Service toEntity(ServiceDTO serviceDTO);

    List<ServiceDTO> mapToListDto(List<Service> services);

    List<Service> toListEntity(List<ServiceDTO> serviceDTOs);

}
