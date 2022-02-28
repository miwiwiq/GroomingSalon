package com.znvks.salon.dao;

import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Form_;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.Pet_;
import com.znvks.salon.entity.Reservation;
import com.znvks.salon.entity.Reservation_;
import com.znvks.salon.entity.Service;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.Account_;
import com.znvks.salon.entity.account.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReservationDao {

    private static final ReservationDao INSTANCE = new ReservationDao();

    public static ReservationDao getInstance() {
        return INSTANCE;
    }

    public List<Reservation> getAll(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Reservation> criteria = cb.createQuery(Reservation.class);
        Root<Reservation> root = criteria.from(Reservation.class);
        return session.createQuery(criteria).getResultList();
    }

    public List<Reservation> getOrdersByAcc(Session session, Account account) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Reservation> criteria = cb.createQuery(Reservation.class);
        Root<Reservation> root = criteria.from(Reservation.class);
        Join<Reservation, Form> formJoin = root.join(Reservation_.form);
        Join<Form, Pet> petJoin = formJoin.join(Form_.pet);
        Join<Pet, User> userJoin = petJoin.join(Pet_.user);
        criteria.select(root).where(cb.equal(userJoin.get(Account_.username), account.getUsername()));
        return session.createQuery(criteria).getResultList();
    }

    public List<Reservation> getOrdersByForm(Session session, Form form) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Reservation> criteria = cb.createQuery(Reservation.class);
        Root<Reservation> root = criteria.from(Reservation.class);
        Join<Reservation, Form> formJoin = root.join(Reservation_.form);
        Join<Form, Pet> petJoin = formJoin.join(Form_.pet);
        petJoin.join(Pet_.user);
        criteria.select(root).where(cb.equal(formJoin.get(Form_.id), form.getId()));
        return session.createQuery(criteria).getResultList();
    }

    public void add(Session session, Reservation reservation) {
        double finalPrice = reservation.getForm().getServices().stream().mapToDouble(Service::getPrice).sum();
        reservation.setFinalPrice(finalPrice);
        session.save(reservation);
    }

    public void update(Session session, Reservation reservation){
        session.merge(reservation);
    }

    public void delete(Session session, Reservation reservation){
        session.delete(reservation);
    }

}
