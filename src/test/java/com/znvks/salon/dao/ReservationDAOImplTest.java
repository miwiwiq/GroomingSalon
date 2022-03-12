package com.znvks.salon.dao;

import com.znvks.salon.config.DbConfigTest;
import com.znvks.salon.entity.Reservation;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DbConfigTest.class)
@Transactional
class ReservationDAOImplTest {

    @Autowired
    private ReservationDAO reservationDAO;
    @Autowired
    private FormDAO formDAO;
    @Autowired
    private AccountDAO accountDAO;
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
        List<Reservation> results = reservationDAO.getAll();
        MatcherAssert.assertThat(results, Matchers.hasSize(3));
    }

    @Test
    void getById() {
        Optional<Reservation> orderById = reservationDAO.getById(1L);
        orderById.ifPresent(order -> MatcherAssert.assertThat(
                orderById.get().getForm().getPet().getName(), Matchers.containsString("name1")));
    }

    @Test
    void testFindByForm() {
        Optional<Reservation> results = reservationDAO.getOrdersByForm(
                formDAO.getFormsByAcc(accountDAO.getAccByUsername("user1").get()).get(0));
        Assertions.assertNotNull(results.get());
    }

    @Test
    void testFindByAcc() {
        List<Reservation> results = reservationDAO.getOrdersByAcc(accountDAO.getAccByUsername("user1").get());
        MatcherAssert.assertThat(results, Matchers.hasSize(2));
    }
}
