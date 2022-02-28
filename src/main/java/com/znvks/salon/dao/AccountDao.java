package com.znvks.salon.dao;

import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.Account_;
import com.znvks.salon.entity.account.Admin;
import com.znvks.salon.entity.account.User;
import com.znvks.salon.entity.account.User_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountDao {

    private static final AccountDao INSTANCE = new AccountDao();

    public static AccountDao getInstance() {
        return INSTANCE;
    }

    public List<Account> getAll(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = cb.createQuery(Account.class);
        Root<Account> root = criteria.from(Account.class);
        return session.createQuery(criteria).getResultList();
    }

    public List<User> getAllUsers(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        return session.createQuery(criteria).getResultList();
    }

    public List<Admin> getAllAdmins(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Admin> criteria = cb.createQuery(Admin.class);
        Root<Admin> root = criteria.from(Admin.class);
        return session.createQuery(criteria).getResultList();
    }

    public void add(Session session, Account account) {
        session.save(account);
    }

    public void update(Session session, Account account){
        session.merge(account);
    }

    public void delete(Session session, Account account){
        session.delete(account);
    }

    public Account getAccByUsername(Session session, String username) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = cb.createQuery(Account.class);
        Root<Account> root = criteria.from(Account.class);
        criteria.select(root).where(cb.equal(root.get(Account_.username), username));
        return session.createQuery(criteria).getResultList().get(0);
    }

    public List<User> getAccByName(Session session, String name) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).where(cb.equal(root.get(User_.name), name));
        return session.createQuery(criteria).getResultList();
    }

    public List<User> getAccBySurname(Session session, String surname) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).where(cb.equal(root.get(User_.surname), surname));
        return session.createQuery(criteria).getResultList();
    }

    public boolean isAuthenticate(Session session, Account account){
        return getAccByUsername(session, account.getUsername()).getPassword().equals(account.getPassword());
    }


}
