package com.znvks.salon.dao.impl;

import com.znvks.salon.dao.PetDAO;
import com.znvks.salon.entity.Kind;
import com.znvks.salon.entity.Kind_;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.Pet_;
import com.znvks.salon.entity.account.Account_;
import com.znvks.salon.entity.account.User;
import com.znvks.salon.entity.account.User_;
import lombok.Cleanup;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;
import java.util.List;

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
        return session.createQuery(criteria).getResultList();
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
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<Pet> getPetByName(String name) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pet> criteria = cb.createQuery(Pet.class);
        Root<Pet> root = criteria.from(Pet.class);
        criteria.select(root).where(cb.equal(root.get(Pet_.name), name));
        return session.createQuery(criteria).getResultList();
    }

    public void save(Session session, Pet pet) {
        session.save(pet);
    }

}
