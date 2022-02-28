package com.znvks.salon.dao;

import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Kind;
import com.znvks.salon.entity.Kind_;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.Pet_;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.Account_;
import com.znvks.salon.entity.account.User;
import com.znvks.salon.entity.account.User_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PetDao {

    private static final PetDao INSTANCE = new PetDao();

    public static PetDao getInstance() {
        return INSTANCE;
    }

    public List<Pet> getAll(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pet> criteria = cb.createQuery(Pet.class);
        Root<Pet> root = criteria.from(Pet.class);
        root.join(Pet_.kind);
        root.join(Pet_.user);
        return session.createQuery(criteria).getResultList();
    }

    public List<Pet> getPetsByAcc(Session session, User user) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pet> criteria = cb.createQuery(Pet.class);
        Root<User> root = criteria.from(User.class);
        ListJoin<User, Pet> petJoin = root.join(User_.pets);
        petJoin.join(Pet_.kind);
        criteria.select(petJoin).where(cb.equal(root.get(Account_.username), user.getUsername()));
        return session.createQuery(criteria).getResultList();
    }

    public List<Pet> getPetByKind(Session session, String kind) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pet> criteria = cb.createQuery(Pet.class);
        Root<Pet> root = criteria.from(Pet.class);
        root.join(Pet_.user);
        Join<Pet, Kind> kindJoin = root.join(Pet_.kind);
        criteria.select(root).where(cb.equal(kindJoin.get(Kind_.kind), kind));
        return session.createQuery(criteria).getResultList();
    }

    public List<Pet> getPetByName(Session session, String name) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pet> criteria = cb.createQuery(Pet.class);
        Root<Pet> root = criteria.from(Pet.class);
        criteria.select(root).where(cb.equal(root.get(Pet_.name), name));
        return session.createQuery(criteria).getResultList();
    }

    private boolean isKindNotExist(Session session, Kind kind) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Kind> criteria = cb.createQuery(Kind.class);
        Root<Kind> root = criteria.from(Kind.class);
        criteria.select(root).where(cb.equal(root.get(Kind_.kind), kind.getKind().toLowerCase()));
        return session.createQuery(criteria).getResultList().isEmpty();
    }

    public void add(Session session, Pet pet) {
        if (isKindNotExist(session, pet.getKind())) {
            session.save(pet.getKind());
        }
        session.save(pet);
    }

    public void update(Session session, Pet pet){
        session.merge(pet);
    }

    public void delete(Session session, Pet pet){
        session.delete(pet);
    }


}
