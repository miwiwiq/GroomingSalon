package com.znvks.dao;

import com.znvks.salon.dao.AccountDao;
import com.znvks.salon.dao.PetDao;
import com.znvks.salon.entity.Kind;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PetDaoTest {

    private PetDao petDao = PetDao.getInstance();
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

            List<Pet> results = petDao.getAll(session);
            assertThat(results, hasSize(3));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindByAcc() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Pet> results = petDao.getPetsByAcc(session, (User) AccountDao.getInstance().getAccByUsername(session, "user5"));
            assertThat(results, hasSize(2));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindByName() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Pet> results = petDao.getPetByName(session, "rf");
            assertThat(results, hasSize(1));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindByKind() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Pet> results = petDao.getPetByKind(session, "rat");
            assertThat(results, hasSize(3));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testAdd() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Pet pet = Pet.builder()
                    .name("kiki")
                    .kind(Kind.builder().kind("dog").build())
                    .user((User) AccountDao.getInstance().getAccByUsername(session, "user2"))
                    .build();
            petDao.add(session, pet);
            session.getTransaction().commit();
            session.beginTransaction();
            List<Pet> results = petDao.getPetsByAcc(session, (User) AccountDao.getInstance().getAccByUsername(session, "user2"));
            assertThat(results, hasSize(3));
            session.getTransaction().commit();
        }
    }

}
