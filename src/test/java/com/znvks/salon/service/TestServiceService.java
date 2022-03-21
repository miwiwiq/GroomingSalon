package com.znvks.salon.service;

import com.znvks.salon.config.DbConfigTest;
import com.znvks.salon.dao.ServiceDAO;
import com.znvks.salon.dto.ServiceDTO;
import com.znvks.salon.entity.Service;
import com.znvks.salon.mapper.ServiceMapper;
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
 class TestServiceService {

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private ServiceMapper serviceMapper;

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
        List<ServiceDTO> services = serviceService.getAll();
        System.out.println(services);
        assertEquals(5, services.size());
    }

    @Test
    void getById() {
        Optional<ServiceDTO> serviceById = serviceService.getById(1L);
        serviceById.ifPresent(service -> MatcherAssert.assertThat(serviceById.get().getName(), Matchers.containsString("service1")));
    }

//    @Test
//    void testFindByForm() {
//        List<Service> results = serviceService.getServiceByForm(formDAO.getFormsByAcc(accountDAO.getAccByUsername("user1").get()).get(0));
//        MatcherAssert.assertThat(results, Matchers.hasSize(3));
//    }

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
    void update() {
        Optional<ServiceDTO> optionalServiceDTO = serviceService.getById(4L);
        if (optionalServiceDTO.isPresent()){
           ServiceDTO serviceDTO = optionalServiceDTO.get();
           serviceDTO.setName("service100");
           Service serv = serviceMapper.toEntity(serviceDTO);
           serv.setId(serviceDTO.getId());
           serviceService.update(serv);
            Optional<ServiceDTO> updated = serviceService.getById(4L);
            updated.ifPresent(s -> MatcherAssert.assertThat(
                    s.getName(), Matchers.containsString("service1000")));
        }
    }

}
