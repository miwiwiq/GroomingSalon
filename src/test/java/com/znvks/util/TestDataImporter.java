package com.znvks.util;

import com.znvks.salon.dao.AccountDao;
import com.znvks.salon.dao.PetDao;
import com.znvks.salon.entity.Condition;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Kind;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.Reservation;
import com.znvks.salon.entity.Service;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.Admin;
import com.znvks.salon.entity.account.Level;
import com.znvks.salon.entity.account.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TestDataImporter {

    private static TestDataImporter INSTANCE = new TestDataImporter();

    public static TestDataImporter getInstance() {
        return INSTANCE;
    }

    public void importTestData(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

//            Organization microsoft = saveOrganization(session, "Microsoft");
//            Organization apple = saveOrganization(session, "Apple");
//            Organization google = saveOrganization(session, "Google");

            saveAdmin(session, "admin", "adminpass", Level.FIRST);
            saveAdmin(session, "admin2", "admin2pass", Level.SECOND);
            User user1 = saveUser(session, "user1", "user1pass", "user1name", "user1surname", "1", "1@");
            User user2 = saveUser(session, "user2", "user2pass", "user2name", "user2surname", "2", "2@");
            User user3 = saveUser(session, "user3", "user3pass", "user3name", "user2surname", "3", "3@");

            Kind kind1 = saveKind(session, "cat");
            Kind kind2 = saveKind(session, "dog");

            Pet pet1 = savePet(session, "name1", kind1, user1);
            Pet pet2 = savePet(session, "name2", kind2, user1);
            Pet pet3 = savePet(session, "name3", kind1, user1);
            Pet pet4 = savePet(session, "name3", kind2, user2);
            Pet pet5 = savePet(session, "name5", kind2, user2);

            Service service1 = saveService(session, "service1", 10, 12.20);
            Service service2 = saveService(session, "service2", 20, 15.20);
            Service service3 = saveService(session, "service3", 15, 13.20);
            Service service4 = saveService(session, "service4", 17, 8.10);
            Service service5 = saveService(session, "service4", 25, 19.20);

            List<Service> services = new ArrayList<>();
            services.add(service1);
            services.add(service2);
            services.add(service3);

            Form form1 = saveForm(session, Condition.WAITING, pet1, services);
            Form form2 = saveForm(session, Condition.WAITING, pet2, services);
            Form form3 = saveForm(session, Condition.WAITING, pet5, services);

            saveReservation(session,form1, LocalDate.now(), 15, 3, LocalTime.now());
            saveReservation(session,form2, LocalDate.now(), 20, 5, LocalTime.now());
            saveReservation(session,form3, LocalDate.now(), 35, 4, LocalTime.now());

            session.getTransaction().commit();


        }
    }

    private Admin saveAdmin(Session session, String username, String password, Level level) {
        Admin admin = new Admin(username, password, level);
        session.save(admin);
        return admin;
    }

    private User saveUser(Session session, String username, String password, String name, String surname, String phone, String email) {
        User user = new User(username, password, name, surname, phone, email, null);
        session.save(user);
        return user;
    }

    private Pet savePet(Session session, String name, Kind kind, User user) {
        Pet pet = new Pet(name, kind, user);
        session.save(pet);
        return pet;
    }

    private Kind saveKind(Session session, String kindName) {
        Kind kind = new Kind(kindName);
        session.save(kind);
        return kind;
    }

    private Service saveService(Session session, String name, int duration, double price) {
        Service service = new Service(name, duration, price);
        session.save(service);
        return service;
    }

    private Form saveForm(Session session, Condition condition, Pet pet, List<Service> services) {
        Form form = new Form(condition, pet, services);
        session.save(form);
        return form;
    }

    private Reservation saveReservation(Session session, Form form, LocalDate date, double finalPrice, int rating, LocalTime time) {
        Reservation reservation = new Reservation(date, time, finalPrice, rating, form);
        session.save(reservation);
        return reservation;
    }

}
