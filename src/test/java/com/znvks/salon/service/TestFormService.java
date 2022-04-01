package com.znvks.salon.service;

import com.znvks.salon.config.DbConfigTest;
import com.znvks.salon.model.dto.FormDTO;
import com.znvks.salon.model.entity.Condition;
import com.znvks.salon.model.service.AccountService;
import com.znvks.salon.model.service.FormService;
import com.znvks.salon.model.service.PetService;
import com.znvks.salon.model.service.ServiceService;
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
class TestFormService {

    @Autowired
    private FormService formService;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private PetService petService;
    @Autowired
    AccountService accountService;

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
        assertEquals(4, accounts.size());
    }

    @Test
    void getById() {
        Optional<FormDTO> form = formService.getById(1L);
        System.out.println(form);
        form.ifPresent(f -> MatcherAssert.assertThat(
                f.getPet().getName(), Matchers.containsString("name1")));
    }

    @Test
    void testFindFormsByAcc() {
        if (accountService.getAccByUsername("user1").isPresent()){
            List<FormDTO> results = formService.getFormsByAcc(accountService.getAccByUsername("user1").get());
            MatcherAssert.assertThat(results, Matchers.hasSize(2));
        }
    }

    @Test
    void testFindFormsByCondition() {
        List<FormDTO> results = formService.getFormsByCondition(Condition.WAITING);
        MatcherAssert.assertThat(results, Matchers.hasSize(4));
    }

    @Test
    void save() {
        FormDTO formDTO = FormDTO.builder().condition(Condition.WAITING).services(serviceService.getServiceByDuration(17)).pet(petService.getPetByName("name1").get(0)).build();
        Long id = formService.save(formDTO);
        Optional<FormDTO> form = formService.getById(id);
        assertTrue(form.isPresent());
    }

    @Test
    void update() {
        Optional<FormDTO> optional = formService.getById(3L);
        optional.ifPresent(f -> {
            f.setCondition(Condition.DECLINED);
            formService.update(f);
        });
        Optional<FormDTO> acc = formService.getById(3L);
        acc.ifPresent(a -> assertEquals(Condition.DECLINED, a.getCondition()));
    }

    @Test
    void delete() {
        Optional<FormDTO> optional = formService.getById(1L);
        optional.ifPresent(f -> formService.delete(f));
        Optional<FormDTO> byId = formService.getById(1L);
        assertFalse(byId.isPresent());
    }

}
