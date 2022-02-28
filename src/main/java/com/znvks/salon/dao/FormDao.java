package com.znvks.salon.dao;

import com.znvks.salon.entity.Condition;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Form_;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.Pet_;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.Account_;
import com.znvks.salon.entity.account.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FormDao {

    private static final FormDao INSTANCE = new FormDao();

    public static FormDao getInstance() {
        return INSTANCE;
    }

    public List<Form> getAll(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Form> criteria = cb.createQuery(Form.class);
        Root<Form> root = criteria.from(Form.class);
        return session.createQuery(criteria).getResultList();
    }

    public List<Form> getFormsByAcc(Session session, Account account) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Form> criteria = cb.createQuery(Form.class);
        Root<Form> root = criteria.from(Form.class);
        Join<Form, Pet> petJoin = root.join(Form_.pet);
        Join<Pet, User> userJoin = petJoin.join(Pet_.user);
        criteria.select(root).where(cb.equal(userJoin.get(Account_.username), account.getUsername()));
        return session.createQuery(criteria).getResultList();
    }

    public List<Form> getFormsByCondition(Session session, Condition condition) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Form> criteria = cb.createQuery(Form.class);
        Root<Form> root = criteria.from(Form.class);
        Join<Form, Pet> petJoin = root.join(Form_.pet);
        petJoin.join(Pet_.user);
        criteria.select(root).where(cb.equal(root.get(Form_.condition), condition));
        return session.createQuery(criteria).getResultList();
    }

    public void add(Session session, Form form) {
        session.save(form);
    }

    public void update(Session session, Form form) {
        session.merge(form);
    }

    public void delete(Session session, Form form) {
        session.delete(form);
    }

}
