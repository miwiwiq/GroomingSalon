package com.znvks.salon.service;

import com.znvks.salon.config.DbConfigTest;
import com.znvks.salon.model.dto.ReservationDTO;
import com.znvks.salon.model.entity.Condition;
import com.znvks.salon.model.service.AccountService;
import com.znvks.salon.model.service.FormService;
import com.znvks.salon.model.service.ReservationService;
import com.znvks.salon.util.TestDataImporter;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DbConfigTest.class)
@Transactional
 class TestReservationService {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private FormService formService;
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
        List<ReservationDTO> reservations = reservationService.getAll();
        System.out.println(reservations);
        assertEquals(3, reservations.size());
    }

    @Test
    void getById() {
        Optional<ReservationDTO> reservations = reservationService.getById(1L);
        System.out.println(reservations);
        reservations.ifPresent(r -> MatcherAssert.assertThat(
                r.getForm().getPet().getName(), Matchers.containsString("name1")));
    }

    @Test
    void testFindByForm() {
        Optional<ReservationDTO> results = reservationService.getOrdersByForm(
                formService.getFormsByCondition(Condition.WAITING).get(0));
        Assertions.assertNotNull(results.get());
    }

    @Test
    void testFindByAcc() {
        List<ReservationDTO> results = reservationService.getOrdersByAcc(accountService.getAccByUsername("user1").get());
        MatcherAssert.assertThat(results, Matchers.hasSize(2));
    }

    @Test
    void save() {
        ReservationDTO reservationDTO = ReservationDTO.builder().date(LocalDate.now()).form(formService.getFormsByCondition(Condition.WAITING).get(0)).rating(5).build();
        Long id = reservationService.save(reservationDTO);
        Optional<ReservationDTO> byId = reservationService.getById(id);
        assertTrue(byId.isPresent());
    }

    @Test
    void update() {
        Optional<ReservationDTO> optional = reservationService.getById(1L);
        optional.ifPresent(r -> {
            r.setRating(1);
            reservationService.update(r);
        });
        Optional<ReservationDTO> r = reservationService.getById(1L);
        r.ifPresent(p -> assertEquals(1, p.getRating()));
    }

    @Test
    void delete() {
        Optional<ReservationDTO> optional = reservationService.getById(1L);
        optional.ifPresent(r -> reservationService.delete(r));
        Optional<ReservationDTO> byId = reservationService.getById(1L);
        assertFalse(byId.isPresent());
    }
}
