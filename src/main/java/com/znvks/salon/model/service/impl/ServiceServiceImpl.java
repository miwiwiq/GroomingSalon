package com.znvks.salon.model.service.impl;

import com.znvks.salon.model.dao.ServiceDAO;
import com.znvks.salon.model.dto.FormDTO;
import com.znvks.salon.model.dto.ServiceDTO;
import com.znvks.salon.model.entity.Service;
import com.znvks.salon.model.service.ServiceService;
import com.znvks.salon.model.mapper.FormMapper;
import com.znvks.salon.model.mapper.ServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        return serviceMapper.mapToListDto(services);
    }

    @Override
    public List<ServiceDTO> getServiceByName(String name) {
        List<Service> services = serviceDAO.getServiceByName(name);
        return serviceMapper.mapToListDto(services);
    }

    @Override
    public List<ServiceDTO> getServiceByDuration(int duration) {
        List<Service> services = serviceDAO.getServiceByDuration(duration);
        return serviceMapper.mapToListDto(services);
    }

    @Override
    public List<ServiceDTO> getServiceByPrice(double price) {
        List<Service> services = serviceDAO.getServiceByPrice(price);
        return serviceMapper.mapToListDto(services);
    }


    @Override
    public Optional<ServiceDTO> getById(Long id) {
        Optional<Service> service = serviceDAO.getById(id);
        return Optional.ofNullable(serviceMapper.mapToDto(service.orElse(null)));
    }

    @Override
    @Transactional
    public Long save(ServiceDTO service) {
        return serviceDAO.save(serviceMapper.mapToEntity(service));
    }

    @Override
    @Transactional
    public void update(ServiceDTO service) {
        serviceDAO.update(serviceMapper.mapToEntity(service));
    }

    @Override
    @Transactional
    public void delete(ServiceDTO serviceDTO) {
        Service service = serviceDAO.getById(serviceDTO.getId()).orElse(null);
        serviceDAO.delete(service);
    }

    @Override
    public List<ServiceDTO> getAll() {
        List<Service> services = serviceDAO.getAll();
        return serviceMapper.mapToListDto(services);
    }
}
