package com.znvks.salon.service;

import com.znvks.salon.config.DbConfigTest;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DbConfigTest.class)
@Transactional
class TestPetService {

    @Autowired
    private PetService petService;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private AccountService accountService;

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
        List<PetDTO> accounts = petService.getAll();
        System.out.println(accounts);
        assertEquals(5, accounts.size());
    }

    @Test
    void getById() {
        Optional<PetDTO> pet = petService.getById(1L);
        System.out.println(pet);
        pet.ifPresent(p -> MatcherAssert.assertThat(
                p.getName(), Matchers.containsString("name1")));
    }

    @Test
    void testFindByAcc() {
        List<PetDTO> results = petService.getPetsByAcc(accountService.getAccByUsername("user2").get());
        MatcherAssert.assertThat(results, Matchers.hasSize(2));
    }

    @Test
    void testFindByName() {
        List<PetDTO> results = petService.getPetByName("name3");
        MatcherAssert.assertThat(results, Matchers.hasSize(2));
    }

    @Test
    void testFindByKind() {
        List<PetDTO> results = petService.getPetByKind("cat");
        MatcherAssert.assertThat(results, Matchers.hasSize(2));
    }

    @Test
    void save() {
        PetDTO pet = PetDTO.builder().name("cat").kind("mwce").user(accountService.getAccByUsername("user1").get()).build();
        Long id = petService.save(pet);
        Optional<PetDTO> petById = petService.getById(id);
        assertTrue(petById.isPresent());
    }

    @Test
    void update() {
        Optional<PetDTO> optional = petService.getById(1L);
        optional.ifPresent(pet -> {
            pet.setName("qqq");
            petService.update(pet);
        });
        Optional<PetDTO> petById = petService.getById(1L);
        petById.ifPresent(p -> assertEquals("qqq", p.getName()));
    }

    @Test
    void delete() {
        Optional<PetDTO> optional = petService.getById(1L);
        optional.ifPresent(pet -> petService.delete(pet));
        Optional<PetDTO> byId = petService.getById(1L);
        assertFalse(byId.isPresent());
    }
}
