package com.znvks.salon.service;

import com.znvks.salon.config.DbConfigTest;
import com.znvks.salon.dao.AccountDAO;
import com.znvks.salon.dao.PetDAO;
import com.znvks.salon.dto.AccountDTO;
import com.znvks.salon.dto.PetDTO;
import com.znvks.salon.entity.Kind;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.account.User;
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
    private AccountDAO accountDAO;
    @Autowired
    private PetDAO petDAO;

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
    void save(){
        Pet pet = Pet.builder().name("fgh").kind(Kind.builder().kind("cat").build()).user( (User) accountDAO.getAccByUsername("user2").get()).build();
        Long id = petService.save(pet);
        Optional<PetDTO> petById = petService.getById(id);
        assertTrue(petById.isPresent());
        petService.delete(pet);
    }
//
//    @Test
//    void update() {
//        Optional<Pet> byId = petDao.findById(1L);
//        byId.ifPresent(book -> {
//            book.setName("Последнее желание");
//            bookDao.update(book);
//        });
//        Optional<Book> updatedBook = bookDao.findById(1L);
//        updatedBook.ifPresent(book -> assertEquals("Последнее желание", book.getName()));
//    }
}
