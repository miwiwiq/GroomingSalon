package com.znvks.dao;

import com.znvks.salon.dao.AccountDao;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.Admin;
import com.znvks.salon.entity.account.User;
import com.znvks.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class AccountDaoTest {

    private AccountDao accountDao = AccountDao.getInstance();
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

            List<Account> results = accountDao.getAll(session);
            assertThat(results, hasSize(5));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<User> results = accountDao.getAllUsers(session);
            assertThat(results, hasSize(3));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindAllAdmins() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Admin> results = accountDao.getAllAdmins(session);
            assertThat(results, hasSize(2));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindByUsername() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Account result = accountDao.getAccByUsername(session, "admin");
            String password = result.getPassword();
            assertThat(password, containsString("adminpass"));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindByName() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<User> results = accountDao.getAccByName(session, "user1name");
            assertThat(results, hasSize(1));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindBySurname() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<User> results = accountDao.getAccBySurname(session, "user2surname");
            assertThat(results, hasSize(2));
            session.getTransaction().commit();
        }
    }

}
