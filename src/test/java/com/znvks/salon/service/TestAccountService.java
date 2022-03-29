package com.znvks.salon.service;

import com.znvks.salon.config.DbConfigTest;
import com.znvks.salon.model.dto.AccountDTO;
import com.znvks.salon.model.service.AccountService;
import com.znvks.salon.util.TestDataImporter;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DbConfigTest.class)
@Transactional
class TestAccountService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    public void initDb() {
        TestDataImporter.importTestData(sessionFactory);
    }

    @AfterTransaction
    public void finish() {
        sessionFactory.close();
    }

    @Test
    void getAll() {
        List<AccountDTO> accounts = accountService.getAll();
        System.out.println(accounts);
        assertEquals(5, accounts.size());
    }

    @Test
    void getById() {
        Optional<AccountDTO> account = accountService.getById(1L);
        System.out.println(account);
        account.ifPresent(acc -> MatcherAssert.assertThat(
                acc.getUsername(), Matchers.containsString("admin")));
    }

    @Test
    void testFindAllUsers() {
        List<AccountDTO> results = accountService.getAllUsers();
        MatcherAssert.assertThat(results, Matchers.hasSize(3));
    }

    @Test
    void testFindAllAdmins() {
        List<AccountDTO> results = accountService.getAllAdmins();
        MatcherAssert.assertThat(results, Matchers.hasSize(2));
    }

    @Test
    void testFindByUsername() {
        AccountDTO result = accountService.getAccByUsername("admin").get();
        String password = result.getPassword();
        MatcherAssert.assertThat(password, Matchers.containsString("adminpass"));
    }

    @Test
    void testFindByName() {
        List<AccountDTO> results = accountService.getAccByName("user1name");
        MatcherAssert.assertThat(results, Matchers.hasSize(1));
    }

    @Test
    void testFindBySurname() {
        List<AccountDTO> results = accountService.getAccBySurname("user2surname");
        MatcherAssert.assertThat(results, Matchers.hasSize(2));
    }

    @Test
    void save() {
        AccountDTO accountDTO = AccountDTO.builder().username("kto").password("ya").role("user").build();
        Long id = accountService.save(accountDTO);
        Optional<AccountDTO> acc = accountService.getById(id);
        assertTrue(acc.isPresent());
    }

    @Test
    void update() {
        Optional<AccountDTO> optional = accountService.getById(3L);
        optional.ifPresent(acc -> {
            acc.setName("qqq");
            accountService.update(acc);
        });
        Optional<AccountDTO> acc = accountService.getById(3L);
        acc.ifPresent(a -> assertEquals("qqq", a.getName()));
    }

    @Test
    void delete() {
        Optional<AccountDTO> optional = accountService.getById(1L);
        optional.ifPresent(acc -> accountService.delete(acc));
        Optional<AccountDTO> byId = accountService.getById(1L);
        assertFalse(byId.isPresent());
    }
}
