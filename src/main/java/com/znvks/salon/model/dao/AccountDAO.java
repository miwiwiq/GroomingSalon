package com.znvks.salon.model.dao;

import com.znvks.salon.model.entity.account.Account;
import com.znvks.salon.model.entity.account.Admin;
import com.znvks.salon.model.entity.account.User;

import java.util.List;
import java.util.Optional;

public interface AccountDAO extends BaseDAO<Long, Account> {

    List<User> getAllUsers();

    List<Admin> getAllAdmins();

    Optional<Account> getAccByUsername(String username);

    List<User> getAccByName(String name);

    List<User> getAccBySurname(String surname);

    boolean isAuthenticate(Account account);

}
