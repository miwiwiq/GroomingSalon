package com.znvks.salon.dao;

import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Service;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public interface ServiceDAO extends BaseDAO<Long, Service> {

    List<Service> getServiceByForm(Form form);

    List<Service> getServiceByName(String name);

    List<Service> getServiceByDuration(int duration);

    List<Service> getServiceByPrice(double price);
}
