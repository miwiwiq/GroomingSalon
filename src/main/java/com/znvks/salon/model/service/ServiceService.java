package com.znvks.salon.model.service;

import com.znvks.salon.model.dto.FormDTO;
import com.znvks.salon.model.dto.ServiceDTO;

import java.util.List;

public interface ServiceService extends BaseService<ServiceDTO> {

    List<ServiceDTO> getServiceByForm(FormDTO form);

    List<ServiceDTO> getServiceByName(String name);

    List<ServiceDTO> getServiceByDuration(int duration);

    List<ServiceDTO> getServiceByPrice(double price);

}
