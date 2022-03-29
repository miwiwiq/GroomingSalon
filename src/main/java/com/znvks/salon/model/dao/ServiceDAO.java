package com.znvks.salon.model.dao;

import com.znvks.salon.model.entity.Form;
import com.znvks.salon.model.entity.Service;

import java.util.List;

public interface ServiceDAO extends BaseDAO<Long, Service> {

    List<Service> getServiceByForm(Form form);

    List<Service> getServiceByName(String name);

    List<Service> getServiceByDuration(int duration);

    List<Service> getServiceByPrice(double price);
}
