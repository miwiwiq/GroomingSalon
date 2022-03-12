package com.znvks.salon.dao.impl;

import com.znvks.salon.dao.ReservationDAO;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Form_;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.Pet_;
import com.znvks.salon.entity.Reservation;
import com.znvks.salon.entity.Reservation_;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.Account_;
import com.znvks.salon.entity.account.User;
import lombok.Cleanup;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationDAOImpl extends BaseDAOImpl<Long, Reservation> implements ReservationDAO {

    @Override
    public List<Reservation> getOrdersByAcc(Account account) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Reservation> criteria = cb.createQuery(Reservation.class);
        Root<Reservation> root = criteria.from(Reservation.class);
        Join<Reservation, Form> formJoin = root.join(Reservation_.form);
        Join<Form, Pet> petJoin = formJoin.join(Form_.pet);
        Join<Pet, User> userJoin = petJoin.join(Pet_.user);
        criteria.select(root).where(cb.equal(userJoin.get(Account_.username), account.getUsername()));
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public Optional<Reservation> getOrdersByForm(Form form) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Reservation> criteria = cb.createQuery(Reservation.class);
        Root<Reservation> root = criteria.from(Reservation.class);
        Join<Reservation, Form> formJoin = root.join(Reservation_.form);
        Join<Form, Pet> petJoin = formJoin.join(Form_.pet);
        petJoin.join(Pet_.user);
        criteria.select(root).where(cb.equal(formJoin.get(Form_.id), form.getId()));
        return Optional.ofNullable(session.createQuery(criteria).getSingleResult());
    }

//    public void save(Session session, Reservation reservation) {
//        double finalPrice = reservation.getForm().getServices().stream().mapToDouble(Service::getPrice).sum();
//        reservation.setFinalPrice(finalPrice);
//        session.save(reservation);
//    }


}
