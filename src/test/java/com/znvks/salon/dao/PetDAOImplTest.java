package com.znvks.salon.dao;

import com.znvks.salon.config.DbConfigTest;
import com.znvks.salon.model.dao.AccountDAO;
import com.znvks.salon.model.dao.KindDAO;
import com.znvks.salon.model.dao.PetDAO;
import com.znvks.salon.model.entity.Kind;
import com.znvks.salon.model.entity.Pet;
import com.znvks.salon.model.entity.account.User;
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
class PetDAOImplTest {

    @Autowired
    private PetDAO petDAO;
    @Autowired
    private KindDAO kindDAO;
    @Autowired
    private AccountDAO accountDAO;
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

    @Test
    void save() {
        Kind kind = Kind.builder().kind("mwce").build();
        kindDAO.save(kind);
        Pet pet = Pet.builder().name("cat").kind(kind).user((User)accountDAO.getAccByUsername("user1").get()).build();
        Long id = petDAO.save(pet);
        Optional<Pet> petById = petDAO.getById(id);
        assertTrue(petById.isPresent());
    }

    @Test
    void update() {
        Optional<Pet> optional = petDAO.getById(1L);
        optional.ifPresent(pet -> {
            pet.setName("qqq");
            petDAO.update(pet);
        });
        Optional<Pet> petById = petDAO.getById(1L);
        petById.ifPresent(p -> assertEquals("qqq", p.getName()));
    }

    @Test
    void delete() {
        Optional<Pet> optional = petDAO.getById(1L);
        optional.ifPresent(pet -> petDAO.delete(pet));
        Optional<Pet> byId = petDAO.getById(1L);
        assertFalse(byId.isPresent());
    }
}
