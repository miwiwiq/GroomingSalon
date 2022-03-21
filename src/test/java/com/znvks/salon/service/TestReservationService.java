package com.znvks.salon.service;

import com.znvks.salon.config.DbConfigTest;
import com.znvks.salon.dao.FormDAO;
import com.znvks.salon.dao.ReservationDAO;
import com.znvks.salon.dto.FormDTO;
import com.znvks.salon.dto.ReservationDTO;
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
 class TestReservationService {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private ReservationDAO reservationDAO;

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
}
