package com.znvks.salon.service;

import com.znvks.salon.dto.FormDTO;
import com.znvks.salon.dto.ServiceDTO;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceService extends BaseService<ServiceDTO> {

    List<ServiceDTO> getServiceByForm(FormDTO form);

    List<ServiceDTO> getServiceByName(String name);

    List<ServiceDTO> getServiceByDuration(int duration);

    List<ServiceDTO> getServiceByPrice(double price);

}
