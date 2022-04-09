package com.znvks.salon.model.service.impl;

import com.znvks.salon.model.dao.ServiceDAO;
import com.znvks.salon.model.dto.FormDTO;
import com.znvks.salon.model.dto.ServiceDTO;
import com.znvks.salon.model.entity.Service;
import com.znvks.salon.model.service.ServiceService;
import com.znvks.salon.model.mapper.FormMapper;
import com.znvks.salon.model.mapper.ServiceMapper;
import com.znvks.salon.model.util.LoggerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class ServiceServiceImpl implements ServiceService {

    private final ServiceDAO serviceDAO;
    private final ServiceMapper serviceMapper;
    private final FormMapper formMapper;

    @Override
    public List<ServiceDTO> getServiceByForm(FormDTO form) {
        List<Service> services = serviceDAO.getServiceByForm(formMapper.mapToEntity(form));
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, services, form);
        return serviceMapper.mapToListDto(services);
    }

    @Override
    public List<ServiceDTO> getServiceByName(String name) {
        List<Service> services = serviceDAO.getServiceByName(name);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, services, name);
        return serviceMapper.mapToListDto(services);
    }

    @Override
    public List<ServiceDTO> getServiceByDuration(int duration) {
        List<Service> services = serviceDAO.getServiceByDuration(duration);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, services, duration);
        return serviceMapper.mapToListDto(services);
    }

    @Override
    public List<ServiceDTO> getServiceByPrice(double price) {
        List<Service> services = serviceDAO.getServiceByPrice(price);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, services, price);
        return serviceMapper.mapToListDto(services);
    }


    @Override
    public Optional<ServiceDTO> getById(Long id) {
        Optional<Service> service = serviceDAO.getById(id);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, service, id);
        return Optional.ofNullable(serviceMapper.mapToDto(service.orElse(null)));
    }

    @Override
    @Transactional
    public Long save(ServiceDTO service) {
        Long id = serviceDAO.save(serviceMapper.mapToEntity(service));
        log.debug(LoggerUtil.ENTITY_WAS_SAVED_IN_SERVICE, id);
        return id;
    }

    @Override
    @Transactional
    public void update(ServiceDTO service) {
        serviceDAO.update(serviceMapper.mapToEntity(service));
        log.debug(LoggerUtil.ENTITY_WAS_UPDATED_IN_SERVICE, service);
    }

    @Override
    @Transactional
    public void delete(ServiceDTO serviceDTO) {
        Service service = serviceDAO.getById(serviceDTO.getId()).orElse(null);
        serviceDAO.delete(service);
        log.debug(LoggerUtil.ENTITY_WAS_DELETED_IN_SERVICE, serviceDTO);

    }

    @Override
    public List<ServiceDTO> getAll() {
        List<Service> services = serviceDAO.getAll();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE, services);
        return serviceMapper.mapToListDto(services);
    }
}
