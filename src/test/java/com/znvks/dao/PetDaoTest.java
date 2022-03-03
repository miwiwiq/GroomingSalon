package com.znvks.dao;

import com.znvks.salon.dao.AccountDao;
import com.znvks.salon.dao.PetDao;
import com.znvks.salon.entity.Kind;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.User;
import com.znvks.util.TestDataImporter;
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
        TestDataImporter.getInstance().importTestData(sessionFactory);

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
            assertThat(results, hasSize(5));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindByAcc() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Pet> results = petDao.getPetsByAcc(session, (User) AccountDao.getInstance().getAccByUsername(session, "user2"));
            assertThat(results, hasSize(2));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindByName() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Pet> results = petDao.getPetByName(session, "name3");
            assertThat(results, hasSize(2));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindByKind() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Pet> results = petDao.getPetByKind(session, "cat");
            assertThat(results, hasSize(2));
            session.getTransaction().commit();
        }
    }


}
