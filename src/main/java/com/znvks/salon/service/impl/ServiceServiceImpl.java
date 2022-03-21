package com.znvks.salon.service.impl;

import com.znvks.salon.dao.ServiceDAO;
import com.znvks.salon.dto.ServiceDTO;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Service;
import com.znvks.salon.mapper.ServiceMapper;
import com.znvks.salon.service.ServiceService;
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

    @Override
    public List<ServiceDTO> getServiceByForm(Form form) {
        List<Service> services = serviceDAO.getServiceByForm(form);
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
    public Long save(Service service) {
        return serviceDAO.save(service);
    }

    @Override
    public void update(Service service) {
        serviceDAO.update(service);
    }

    @Override
    public void delete(Service service) {
        serviceDAO.delete(service);
    }

    @Override
    public List<ServiceDTO> getAll() {
        List<Service> services = serviceDAO.getAll();
        return serviceMapper.mapToListDto(services);
    }
}
