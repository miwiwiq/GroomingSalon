package com.znvks.dao;

import com.znvks.salon.dao.AccountDao;
import com.znvks.salon.dao.FormDao;
import com.znvks.salon.dao.ServiceDao;
import com.znvks.salon.entity.Condition;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Service;
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

public class FormDaoTest {

    private FormDao formDao = FormDao.getInstance();
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

            List<Form> results = formDao.getAll(session);
            assertThat(results, hasSize(3));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindFormsByAcc() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Form> results = formDao.getFormsByAcc(session, (User) AccountDao.getInstance().getAccByUsername(session,"user1"));
            assertThat(results, hasSize(2));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindFormsByCondition() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Form> results = formDao.getFormsByCondition(session, Condition.WAITING);
            assertThat(results, hasSize(3));
            session.getTransaction().commit();
        }
    }
}
