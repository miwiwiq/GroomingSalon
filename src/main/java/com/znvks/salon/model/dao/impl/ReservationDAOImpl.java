package com.znvks.salon.model.dao.impl;

import com.znvks.salon.model.dao.ReservationDAO;
import com.znvks.salon.model.entity.Form;
import com.znvks.salon.model.entity.Form_;
import com.znvks.salon.model.entity.Pet;
import com.znvks.salon.model.entity.Pet_;
import com.znvks.salon.model.entity.Reservation;
import com.znvks.salon.model.entity.Reservation_;
import com.znvks.salon.model.entity.account.Account;
import com.znvks.salon.model.entity.account.Account_;
import com.znvks.salon.model.entity.account.User;
import com.znvks.salon.model.util.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Slf4j
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
        List<Reservation> resultList = session.createQuery(criteria).getResultList();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, resultList, account);
        return resultList;     }

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
        final Optional<Reservation> reservation = session.createQuery(criteria).getResultStream().findFirst();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, reservation, form);
        return reservation;
    }




}
