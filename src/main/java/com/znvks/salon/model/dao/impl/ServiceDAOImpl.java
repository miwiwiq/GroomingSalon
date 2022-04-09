package com.znvks.salon.model.dao.impl;

import com.znvks.salon.model.dao.ServiceDAO;
import com.znvks.salon.model.entity.Form;
import com.znvks.salon.model.entity.Form_;
import com.znvks.salon.model.entity.Service;
import com.znvks.salon.model.entity.Service_;
import com.znvks.salon.model.util.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;
import java.util.List;

@Slf4j
@Repository
public class ServiceDAOImpl extends BaseDAOImpl<Long, Service> implements ServiceDAO {

    @Override
    public List<Service> getServiceByForm(Form form) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Service> criteria = cb.createQuery(Service.class);
        Root<Form> root = criteria.from(Form.class);
        ListJoin<Form, Service> serviceJoin = root.join(Form_.services);
        criteria.select(serviceJoin).where(cb.equal(root.get(Form_.id), form.getId()));
        List<Service> resultList = session.createQuery(criteria).getResultList();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, resultList, form);
        return resultList;
    }

    @Override
    public List<Service> getServiceByName(String name) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Service> criteria = cb.createQuery(Service.class);
        Root<Service> root = criteria.from(Service.class);
        criteria.select(root).where(cb.equal(root.get(Service_.name), name));
        List<Service> resultList = session.createQuery(criteria).getResultList();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, resultList, name);
        return resultList;
    }

    @Override
    public List<Service> getServiceByDuration(int duration) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Service> criteria = cb.createQuery(Service.class);
        Root<Service> root = criteria.from(Service.class);
        criteria.select(root).where(cb.lessThanOrEqualTo(root.get(Service_.duration), duration));
        List<Service> resultList = session.createQuery(criteria).getResultList();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, resultList, duration);
        return resultList;
    }

    @Override
    public List<Service> getServiceByPrice(double price) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Service> criteria = cb.createQuery(Service.class);
        Root<Service> root = criteria.from(Service.class);
        criteria.select(root).where(cb.lessThanOrEqualTo(root.get(Service_.price), price));
        List<Service> resultList = session.createQuery(criteria).getResultList();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, resultList, price);
        return resultList;
    }

}
