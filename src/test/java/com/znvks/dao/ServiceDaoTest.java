package com.znvks.dao;

import com.znvks.salon.dao.AccountDao;
import com.znvks.salon.dao.FormDao;
import com.znvks.salon.dao.PetDao;
import com.znvks.salon.dao.ServiceDao;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.Service;
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

public class ServiceDaoTest {

    private ServiceDao serviceDao = ServiceDao.getInstance();
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

            List<Service> results = serviceDao.getAll(session);
            assertThat(results, hasSize(5));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindByForm() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Service> results = serviceDao.getServiceByForm(
                    session, FormDao.getInstance().getFormsByAcc(
                            session, AccountDao.getInstance().getAccByUsername(
                                    session,"user1")).get(0));
            assertThat(results, hasSize(3));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindByName() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Service> results = serviceDao.getServiceByName(session, "service4");
            assertThat(results, hasSize(2));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindByDuration() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Service> results = serviceDao.getServiceByDuration(session, 17);
            assertThat(results, hasSize(3));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindByPrice() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Service> results = serviceDao.getServiceByPrice(session, 15);
            assertThat(results, hasSize(3));
            session.getTransaction().commit();
        }
    }
}
