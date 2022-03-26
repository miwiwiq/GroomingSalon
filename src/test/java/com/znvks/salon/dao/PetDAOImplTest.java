package com.znvks.salon.dao;

import com.znvks.salon.config.DbConfigTest;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Pet;
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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DbConfigTest.class)
@Transactional
class PetDAOImplTest {

    @Autowired
    private PetDAO petDAO;
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    public void initDb() {
        TestDataImporter.importTestData(sessionFactory);
    }

    @AfterEach
    public void finish() {
        sessionFactory.close();
    }

    @Test
    void testFindAll() {
        List<Pet> results = petDAO.getAll();
        MatcherAssert.assertThat(results, Matchers.hasSize(5));
    }

    @Test
    void getById() {
        Optional<Pet> petById = petDAO.getById(1L);
        petById.ifPresent(pet -> MatcherAssert.assertThat(
                petById.get().getName(), Matchers.containsString("name1")));
    }

    @Test
    void testFindByAcc() {
        List<Pet> results = petDAO.getPetsByAcc((User) accountDAO.getAccByUsername("user2").get());
        MatcherAssert.assertThat(results, Matchers.hasSize(2));
    }

    @Test
    void testFindByName() {
        List<Pet> results = petDAO.getPetByName("name3");
        MatcherAssert.assertThat(results, Matchers.hasSize(2));
    }

    @Test
    void testFindByKind() {
        List<Pet> results = petDAO.getPetByKind("cat");
        MatcherAssert.assertThat(results, Matchers.hasSize(2));
    }
}
