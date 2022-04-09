package com.znvks.salon.model.dao.impl;

import com.znvks.salon.model.dao.AccountDAO;
import com.znvks.salon.model.entity.account.Account;
import com.znvks.salon.model.entity.account.Account_;
import com.znvks.salon.model.entity.account.Admin;
import com.znvks.salon.model.entity.account.User;
import com.znvks.salon.model.entity.account.User_;
import com.znvks.salon.model.util.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class AccountDAOImpl extends BaseDAOImpl<Long, Account> implements AccountDAO {

    @Override
    public List<User> getAllUsers() {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        List<User> resultList = session.createQuery(criteria).getResultList();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO, resultList);
        return resultList;
    }

    @Override
    public List<Admin> getAllAdmins() {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Admin> criteria = cb.createQuery(Admin.class);
        Root<Admin> root = criteria.from(Admin.class);
        criteria.select(root);
        List<Admin> resultList = session.createQuery(criteria).getResultList();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO, resultList);
        return resultList;
    }

    @Override
    public Optional<Account> getAccByUsername(String username) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = cb.createQuery(Account.class);
        Root<Account> root = criteria.from(Account.class);
        criteria.select(root).where(cb.equal(root.get(Account_.username), username));
        Optional<Account> account = session.createQuery(criteria).getResultStream().findFirst();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, account, username);
        return account;
    }

    @Override
    public List<User> getAccByName(String name) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).where(cb.equal(root.get(User_.name), name));
        List<User> resultList = session.createQuery(criteria).getResultList();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO, resultList);
        return resultList;
    }

    @Override
    public List<User> getAccBySurname(String surname) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).where(cb.equal(root.get(User_.surname), surname));
        List<User> resultList = session.createQuery(criteria).getResultList();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO, resultList);
        return resultList;    }

    @Override
    public boolean isAuthenticate(Account account) {
        Optional<Account> checkedAcc = getAccByUsername(account.getUsername());
        return checkedAcc.map(value -> value.getPassword().equals(account.getPassword())).orElse(false);
    }

}
