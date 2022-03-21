package com.znvks.salon.service;

import com.znvks.salon.config.DbConfigTest;
import com.znvks.salon.dao.AccountDAO;
import com.znvks.salon.dao.ServiceDAO;
import com.znvks.salon.dto.AccountDTO;
import com.znvks.salon.dto.ServiceDTO;
import com.znvks.salon.util.TestDataImporter;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hibernate.SessionFactory;
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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DbConfigTest.class)
@Transactional
public class TestAccountService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private AccountDAO accountDAO;

    @BeforeEach
    public void initDb() {
        TestDataImporter.importTestData(sessionFactory);
    }

    @AfterTestMethod
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
    void getById(){
        Optional<AccountDTO> account = accountService.getById(1L);
        System.out.println(account);
        account.ifPresent(acc -> MatcherAssert.assertThat(
                acc.getUsername(), Matchers.containsString("admin")));
    }
}
