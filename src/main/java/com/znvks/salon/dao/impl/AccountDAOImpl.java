package com.znvks.salon.dao.impl;

import com.znvks.salon.dao.AccountDAO;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.Account_;
import com.znvks.salon.entity.account.Admin;
import com.znvks.salon.entity.account.User;
import com.znvks.salon.entity.account.User_;
import lombok.Cleanup;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountDAOImpl extends BaseDAOImpl<Long, Account> implements AccountDAO {

    @Override
    public List<User> getAllUsers() {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<Admin> getAllAdmins() {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Admin> criteria = cb.createQuery(Admin.class);
        Root<Admin> root = criteria.from(Admin.class);
        criteria.select(root);
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public Optional<Account> getAccByUsername(String username) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = cb.createQuery(Account.class);
        Root<Account> root = criteria.from(Account.class);
        criteria.select(root).where(cb.equal(root.get(Account_.username), username));
        return Optional.ofNullable(session.createQuery(criteria).getSingleResult());
    }

    @Override
    public List<User> getAccByName(String name) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).where(cb.equal(root.get(User_.name), name));
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<User> getAccBySurname(String surname) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).where(cb.equal(root.get(User_.surname), surname));
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public boolean isAuthenticate(Account account) {
        Optional<Account> checkedAcc = getAccByUsername(account.getUsername());
        return checkedAcc.map(value -> value.getPassword().equals(account.getPassword())).orElse(false);
    }


}
