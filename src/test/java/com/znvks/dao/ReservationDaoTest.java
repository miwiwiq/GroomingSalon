package com.znvks.dao;

import com.znvks.salon.dao.AccountDao;
import com.znvks.salon.dao.FormDao;
import com.znvks.salon.dao.ReservationDao;
import com.znvks.salon.dao.ServiceDao;
import com.znvks.salon.entity.Condition;
import com.znvks.salon.entity.Kind;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.Reservation;
import com.znvks.salon.entity.Service;
import com.znvks.salon.entity.account.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class ReservationDaoTest {

    private ReservationDao reservationDao = ReservationDao.getInstance();
    private SessionFactory sessionFactory;

    @Before
    public void initDb() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @After
    public void finish() {
        sessionFactory.close();
    }

    @Test
    public void testFindAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Reservation> results = reservationDao.getAll(session);
            assertThat(results, hasSize(1));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindByForm() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Reservation> results = reservationDao.getOrdersByForm(
                    session, FormDao.getInstance().getFormsByAcc(
                            session, AccountDao.getInstance().getAccByUsername(
                                    session,"user5")).get(0));
            assertThat(results, hasSize(1));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindByAcc() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Reservation> results = reservationDao.getOrdersByAcc(session, AccountDao.getInstance().getAccByUsername(session,"user5"));
            assertThat(results, hasSize(1));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testAdd() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Reservation reservation = Reservation
                    .builder()
                    .form(FormDao.getInstance().getFormsByAcc(
                            session, AccountDao.getInstance().getAccByUsername(
                                    session,"user5")).get(0))
                    .date(LocalDate.now())
                    .time(LocalTime.now())
                    .build();

            reservationDao.add(session, reservation);
            session.getTransaction().commit();
            session.beginTransaction();
            List<Reservation> results = reservationDao.getAll(session);
            assertThat(results, hasSize(1));
            session.getTransaction().commit();
        }
    }
}
