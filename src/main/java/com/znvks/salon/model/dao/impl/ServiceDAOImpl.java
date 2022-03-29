package com.znvks.salon.model.dao.impl;

import com.znvks.salon.model.dao.ServiceDAO;
import com.znvks.salon.model.entity.Form;
import com.znvks.salon.model.entity.Form_;
import com.znvks.salon.model.entity.Service;
import com.znvks.salon.model.entity.Service_;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;
import java.util.List;

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
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<Service> getServiceByName(String name) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Service> criteria = cb.createQuery(Service.class);
        Root<Service> root = criteria.from(Service.class);
        criteria.select(root).where(cb.equal(root.get(Service_.name), name));
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<Service> getServiceByDuration(int duration) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Service> criteria = cb.createQuery(Service.class);
        Root<Service> root = criteria.from(Service.class);
        criteria.select(root).where(cb.lessThanOrEqualTo(root.get(Service_.duration), duration));
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<Service> getServiceByPrice(double price) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Service> criteria = cb.createQuery(Service.class);
        Root<Service> root = criteria.from(Service.class);
        criteria.select(root).where(cb.lessThanOrEqualTo(root.get(Service_.price), price));
        return session.createQuery(criteria).getResultList();
    }

}
