package com.znvks.dao;

import com.znvks.salon.dao.AccountDao;
import com.znvks.salon.entity.Kind;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.Admin;
import com.znvks.salon.entity.account.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class AccountDaoTest {

    private AccountDao accountDao = AccountDao.getInstance();
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

            List<Account> results = accountDao.getAll(session);
            assertThat(results, hasSize(2));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<User> results = accountDao.getAllUsers(session);
            assertThat(results, hasSize(4));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindAllAdmins() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Admin> results = accountDao.getAllAdmins(session);
            assertThat(results, hasSize(1));
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

            List<User> results = accountDao.getAccByName(session, "name");
            assertThat(results, hasSize(3));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindBySurname() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<User> results = accountDao.getAccBySurname(session, "surname");
            assertThat(results, hasSize(3));
            session.getTransaction().commit();
        }
    }

    @Test
    public void testAdd() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = User.builder()
                    .username("user6")
                    .password("qwe")
                    .name("name")
                    .surname("surname")
                    .phoneNumber("12134")
                    .email("ewmc@gmail.com")
                    .build();
            accountDao.add(session, user);
            session.getTransaction().commit();
            session.beginTransaction();
            Account result = accountDao.getAccByUsername(session, "user6");
            String password = result.getPassword();
            assertThat(password, containsString("qwe"));
            session.getTransaction().commit();
        }
    }
}
