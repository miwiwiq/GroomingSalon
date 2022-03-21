package com.znvks.salon.service;

import com.znvks.salon.config.DbConfigTest;
import com.znvks.salon.dao.FormDAO;
import com.znvks.salon.dao.PetDAO;
import com.znvks.salon.dto.FormDTO;
import com.znvks.salon.dto.PetDTO;
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
class TestFormService {

    @Autowired
    private FormService formService;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private FormDAO formDAO;

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
        List<FormDTO> accounts = formService.getAll();
        System.out.println(accounts);
        assertEquals(5, accounts.size());
    }

    @Test
    void getById() {
        Optional<FormDTO> form = formService.getById(1L);
        System.out.println(form);
        form.ifPresent(f -> MatcherAssert.assertThat(
                f.getPet().getName(), Matchers.containsString("name1")));
    }

}
