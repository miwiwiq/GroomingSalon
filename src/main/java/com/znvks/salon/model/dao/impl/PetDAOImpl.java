package com.znvks.salon.model.dao.impl;

import com.znvks.salon.model.dao.PetDAO;
import com.znvks.salon.model.entity.Kind;
import com.znvks.salon.model.entity.Kind_;
import com.znvks.salon.model.entity.Pet;

import com.znvks.salon.model.entity.Pet_;
import com.znvks.salon.model.entity.Service;
import com.znvks.salon.model.entity.account.Account_;
import com.znvks.salon.model.entity.account.User;
import com.znvks.salon.model.entity.account.User_;
import com.znvks.salon.model.util.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;
import java.util.List;

@Slf4j
@Repository
public class PetDAOImpl extends BaseDAOImpl<Long, Pet> implements PetDAO {

    @Override
    public List<Pet> getPetsByAcc(User user) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pet> criteria = cb.createQuery(Pet.class);
        Root<User> root = criteria.from(User.class);
        ListJoin<User, Pet> petJoin = root.join(User_.pets);
        petJoin.join(Pet_.kind);
        criteria.select(petJoin).where(cb.equal(root.get(Account_.username), user.getUsername()));
        List<Pet> resultList = session.createQuery(criteria).getResultList();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, resultList, user);
        return resultList;
    }

    @Override
    public List<Pet> getPetByKind(String kind) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pet> criteria = cb.createQuery(Pet.class);
        Root<Pet> root = criteria.from(Pet.class);
        root.join(Pet_.user);
        Join<Pet, Kind> kindJoin = root.join(Pet_.kind);
        criteria.select(root).where(cb.equal(kindJoin.get(Kind_.kind), kind));
        List<Pet> resultList = session.createQuery(criteria).getResultList();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, resultList, kind);
        return resultList;
    }

    @Override
    public List<Pet> getPetByName(String name) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pet> criteria = cb.createQuery(Pet.class);
        Root<Pet> root = criteria.from(Pet.class);
        criteria.select(root).where(cb.equal(root.get(Pet_.name), name));
        List<Pet> resultList = session.createQuery(criteria).getResultList();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, resultList, name);
        return resultList;
    }

}
