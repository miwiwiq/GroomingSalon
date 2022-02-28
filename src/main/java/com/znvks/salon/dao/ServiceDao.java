package com.znvks.salon.dao;

import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Form_;
import com.znvks.salon.entity.Kind;
import com.znvks.salon.entity.Kind_;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.Pet_;
import com.znvks.salon.entity.Reservation;
import com.znvks.salon.entity.Service;
import com.znvks.salon.entity.Service_;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.Account_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServiceDao {

    private static final ServiceDao INSTANCE = new ServiceDao();

    public static ServiceDao getInstance() {
        return INSTANCE;
    }

    public List<Service> getAll(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Service> criteria = cb.createQuery(Service.class);
        Root<Service> root = criteria.from(Service.class);
        return session.createQuery(criteria).getResultList();
    }

    public List<Service> getServiceByForm(Session session, Form form) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Service> criteria = cb.createQuery(Service.class);
        Root<Form> root = criteria.from(Form.class);
        ListJoin<Form, Service> serviceJoin = root.join(Form_.services);
        criteria.select(serviceJoin).where(cb.equal(root.get(Form_.id), form.getId()));
        return session.createQuery(criteria).getResultList();
    }

    public List<Service> getServiceByName(Session session, String name) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Service> criteria = cb.createQuery(Service.class);
        Root<Service> root = criteria.from(Service.class);
        criteria.select(root).where(cb.equal(root.get(Service_.name), name));
        return session.createQuery(criteria).getResultList();
    }

    public List<Service> getServiceByDuration(Session session, int duration) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Service> criteria = cb.createQuery(Service.class);
        Root<Service> root = criteria.from(Service.class);
        criteria.select(root).where(cb.lessThanOrEqualTo(root.get(Service_.duration), duration));
        return session.createQuery(criteria).getResultList();
    }

    public List<Service> getServiceByPrice(Session session, double price) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Service> criteria = cb.createQuery(Service.class);
        Root<Service> root = criteria.from(Service.class);
        criteria.select(root).where(cb.lessThanOrEqualTo(root.get(Service_.price), price));
        return session.createQuery(criteria).getResultList();
    }

    public void add(Session session, Service service) {
        session.save(service);
    }

    public void update(Session session, Service service){
        session.merge(service);
    }

    public void delete(Session session, Service service){
        session.delete(service);
    }

}
