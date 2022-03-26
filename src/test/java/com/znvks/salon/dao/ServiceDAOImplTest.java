package com.znvks.salon.dao;

import com.znvks.salon.config.DbConfigTest;
import com.znvks.salon.dto.ServiceDTO;
import com.znvks.salon.entity.Service;
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
class ServiceDAOImplTest {

    @Autowired
    private ServiceDAO serviceDAO;
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
        List<Service> results = serviceDAO.getAll();
        MatcherAssert.assertThat(results, Matchers.hasSize(5));
    }

    @Test
    void getById() {
        Optional<Service> serviceById = serviceDAO.getById(1L);
        serviceById.ifPresent(service -> MatcherAssert.assertThat(serviceById.get().getName(), Matchers.containsString("service1")));
    }

    @Test
    void testFindByForm() {
        List<Service> results = serviceDAO.getServiceByForm(formDAO.getFormsByAcc(accountDAO.getAccByUsername("user1").get()).get(0));
        MatcherAssert.assertThat(results, Matchers.hasSize(3));
    }

    @Test
    void testFindByName() {
        List<Service> results = serviceDAO.getServiceByName("service4");
        MatcherAssert.assertThat(results, Matchers.hasSize(2));
    }

    @Test
    void testFindByDuration() {
        List<Service> results = serviceDAO.getServiceByDuration(17);
        MatcherAssert.assertThat(results, Matchers.hasSize(3));
    }

    @Test
    void testFindByPrice() {
        List<Service> results = serviceDAO.getServiceByPrice(15);
        MatcherAssert.assertThat(results, Matchers.hasSize(3));
    }

    @Test
    void save() {
        Service service = Service.builder().duration(10).name("frgh").price(15).build();
        Long id = serviceDAO.save(service);
        Optional<Service> byId = serviceDAO.getById(id);
        assertTrue(byId.isPresent());
    }

    @Test
    void update() {
        Optional<Service> optional = serviceDAO.getById(1L);
        optional.ifPresent(r -> {
            r.setName("qqqqqq");
            serviceDAO.update(r);
        });
        Optional<Service> r = serviceDAO.getById(1L);
        r.ifPresent(p -> assertEquals("qqqqqq", p.getName()));
    }

    @Test
    void delete() {
        Optional<Service> optional = serviceDAO.getById(1L);
        optional.ifPresent(r -> serviceDAO.delete(r));
        Optional<Service> byId = serviceDAO.getById(1L);
        assertFalse(byId.isPresent());
    }
}
