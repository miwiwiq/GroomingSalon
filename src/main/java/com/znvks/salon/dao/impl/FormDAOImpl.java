package com.znvks.salon.dao.impl;

import com.znvks.salon.dao.FormDAO;
import com.znvks.salon.entity.Condition;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Form_;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.Pet_;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.Account_;
import com.znvks.salon.entity.account.User;
import lombok.Cleanup;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class FormDAOImpl extends BaseDAOImpl<Long, Form> implements FormDAO {

    @Override
    public List<Form> getFormsByAcc(Account account) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Form> criteria = cb.createQuery(Form.class);
        Root<Form> root = criteria.from(Form.class);
        Join<Form, Pet> petJoin = root.join(Form_.pet);
        Join<Pet, User> userJoin = petJoin.join(Pet_.user);
        criteria.select(root).where(cb.equal(userJoin.get(Account_.username), account.getUsername()));
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<Form> getFormsByCondition(Condition condition) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Form> criteria = cb.createQuery(Form.class);
        Root<Form> root = criteria.from(Form.class);
        Join<Form, Pet> petJoin = root.join(Form_.pet);
        petJoin.join(Pet_.user);
        criteria.select(root).where(cb.equal(root.get(Form_.condition), condition));
        return session.createQuery(criteria).getResultList();
    }

}
