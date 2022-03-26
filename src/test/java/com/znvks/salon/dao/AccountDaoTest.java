package com.znvks.salon.dao;

import com.znvks.salon.config.DbConfigTest;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.Admin;
import com.znvks.salon.entity.account.User;
import com.znvks.salon.util.TestDataImporter;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DbConfigTest.class)
@Transactional
class AccountDaoTest {

    @Autowired
    private AccountDAO accountDao;
    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    public void initDb() {
        TestDataImporter.importTestData(sessionFactory);
    }

    @AfterTestMethod
    public void finish() {
        sessionFactory.close();
    }

    @Test
    void testFindAll() {
        List<Account> results = accountDao.getAll();
        System.out.println(results);
        MatcherAssert.assertThat(results, Matchers.hasSize(5));
    }

    @Test
    void testFindAllUsers() {
        List<User> results = accountDao.getAllUsers();
        MatcherAssert.assertThat(results, Matchers.hasSize(3));
    }

    @Test
    void testFindAllAdmins() {
        List<Admin> results = accountDao.getAllAdmins();
        MatcherAssert.assertThat(results, Matchers.hasSize(2));
    }

    @Test
    void getById() {
        Optional<Account> userById = accountDao.getById(1L);
        userById.ifPresent(account -> MatcherAssert.assertThat(
                account.getUsername(), Matchers.containsString("admin")));
    }

    @Test
    void testFindByUsername() {
        Account result = accountDao.getAccByUsername("admin").get();
        String password = result.getPassword();
        MatcherAssert.assertThat(password, Matchers.containsString("adminpass"));
    }

    @Test
    void testFindByName() {
        List<User> results = accountDao.getAccByName("user1name");
        MatcherAssert.assertThat(results, Matchers.hasSize(1));
    }

    @Test
    void testFindBySurname() {
        List<User> results = accountDao.getAccBySurname("user2surname");
        MatcherAssert.assertThat(results, Matchers.hasSize(2));
    }

    @Test
    void save() {
        User user = User.builder().username("kto").password("ya").role("user").build();
        Long id = accountDao.save(user);
        Optional<Account> acc = accountDao.getById(id);
        assertTrue(acc.isPresent());
    }

    @Test
    void update() {
        Optional<Account> optional = accountDao.getById(3L);
        optional.ifPresent(acc -> {
            acc.setPassword("lll");
            accountDao.update(acc);
        });
        Optional<Account> acc = accountDao.getById(3L);
        acc.ifPresent(a -> assertEquals("lll", a.getPassword()));
    }

    @Test
    void delete() {
        Optional<Account> optional = accountDao.getById(1L);
        optional.ifPresent(acc -> accountDao.delete(acc));
        Optional<Account> byId = accountDao.getById(1L);
        assertFalse(byId.isPresent());
    }
}
