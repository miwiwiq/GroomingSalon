package com.znvks.salon.service;

import com.znvks.salon.config.DbConfigTest;
import com.znvks.salon.model.dto.ServiceDTO;
import com.znvks.salon.model.entity.Condition;
import com.znvks.salon.model.service.FormService;
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
class TestServiceService {

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private FormService formService;

    @BeforeEach
    public void initDb() {
        TestDataImporter.importTestData(sessionFactory);
    }

    @AfterTransaction
    public void finish() {
        sessionFactory.close();
    }

    @Test
    void getAll() {
        List<ServiceDTO> services = serviceService.getAll();
        System.out.println(services);
        assertEquals(5, services.size());
    }

    @Test
    void getById() {
        Optional<ServiceDTO> serviceById = serviceService.getById(1L);
        serviceById.ifPresent(service -> MatcherAssert.assertThat(serviceById.get().getName(), Matchers.containsString("service1")));
    }

    @Test
    void testFindByForm() {
        List<ServiceDTO> results = serviceService.getServiceByForm(formService.getFormsByCondition(Condition.WAITING).get(0));
        MatcherAssert.assertThat(results, Matchers.hasSize(3));
    }

    @Test
    void testFindByName() {
        List<ServiceDTO> results = serviceService.getServiceByName("service4");
        MatcherAssert.assertThat(results, Matchers.hasSize(2));
    }

    @Test
    void testFindByDuration() {
        List<ServiceDTO> results = serviceService.getServiceByDuration(17);
        MatcherAssert.assertThat(results, Matchers.hasSize(3));
    }

    @Test
    void testFindByPrice() {
        List<ServiceDTO> results = serviceService.getServiceByPrice(15);
        MatcherAssert.assertThat(results, Matchers.hasSize(3));
    }

    @Test
    void save() {
        ServiceDTO serviceDTO = ServiceDTO.builder().duration(10).name("frgh").price(15).build();
        Long id = serviceService.save(serviceDTO);
        Optional<ServiceDTO> byId = serviceService.getById(id);
        assertTrue(byId.isPresent());
    }

    @Test
    void update() {
        Optional<ServiceDTO> optional = serviceService.getById(1L);
        optional.ifPresent(r -> {
            r.setName("qqqqqq");
            serviceService.update(r);
        });
        Optional<ServiceDTO> r = serviceService.getById(1L);
        r.ifPresent(p -> assertEquals("qqqqqq", p.getName()));
    }

    @Test
    void delete() {
        Optional<ServiceDTO> optional = serviceService.getById(1L);
        optional.ifPresent(r -> serviceService.delete(r));
        Optional<ServiceDTO> byId = serviceService.getById(1L);
        assertFalse(byId.isPresent());
    }

}
