package com.znvks.salon.dao;

import com.znvks.salon.config.DbConfigTest;
import com.znvks.salon.entity.Condition;
import com.znvks.salon.entity.Form;
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
class FormDAOImplTest {

    @Autowired
    private FormDAO formDAO;
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private ServiceDAO serviceDAO;
    @Autowired
    private PetDAO petDAO;
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
        List<Form> results = formDAO.getAll();
        MatcherAssert.assertThat(results, Matchers.hasSize(4));
    }

    @Test
    void getById() {
        Optional<Form> formById = formDAO.getById(1L);
        formById.ifPresent(form -> MatcherAssert.assertThat(
                formById.get().getPet().getName(), Matchers.containsString("name1")));
    }

    @Test
    void testFindFormsByAcc() {
        if (accountDAO.getAccByUsername("user1").isPresent()){
            List<Form> results = formDAO.getFormsByAcc(accountDAO.getAccByUsername("user1").get());
            MatcherAssert.assertThat(results, Matchers.hasSize(2));
        }
    }

    @Test
    void testFindFormsByCondition() {
        List<Form> results = formDAO.getFormsByCondition(Condition.WAITING);
        MatcherAssert.assertThat(results, Matchers.hasSize(4));
    }

    @Test
    void save() {
        Form form = Form.builder().condition(Condition.WAITING).services(serviceDAO.getServiceByDuration(17)).pet(petDAO.getPetByName("name1").get(0)).build();
        Long id = formDAO.save(form);
        Optional<Form> f = formDAO.getById(id);
        assertTrue(f.isPresent());
    }

    @Test
    void update() {
        Optional<Form> optional = formDAO.getById(3L);
        optional.ifPresent(f -> {
            f.setCondition(Condition.DECLINED);
            formDAO.update(f);
        });
        Optional<Form> acc = formDAO.getById(3L);
        acc.ifPresent(a -> assertEquals(Condition.DECLINED, a.getCondition()));
    }

    @Test
    void delete() {
        Optional<Form> optional = formDAO.getById(1L);
        optional.ifPresent(f -> formDAO.delete(f));
        Optional<Form> byId = formDAO.getById(1L);
        assertFalse(byId.isPresent());
    }
}
