package com.znvks.salon.model.dao.impl;

import com.znvks.salon.model.dao.FormDAO;
import com.znvks.salon.model.entity.Condition;
import com.znvks.salon.model.entity.Form;
import com.znvks.salon.model.entity.Form_;
import com.znvks.salon.model.entity.Pet;
import com.znvks.salon.model.entity.Pet_;
import com.znvks.salon.model.entity.account.Account;
import com.znvks.salon.model.entity.account.Account_;
import com.znvks.salon.model.entity.account.User;
import com.znvks.salon.model.util.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Slf4j
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
        List<Form> resultList = session.createQuery(criteria).getResultList();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, resultList, account);
        return resultList;
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
        List<Form> resultList = session.createQuery(criteria).getResultList();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, resultList, condition);
        return resultList;
    }

}
