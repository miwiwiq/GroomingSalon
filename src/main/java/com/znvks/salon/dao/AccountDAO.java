package com.znvks.salon.dao;

import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.Admin;
import com.znvks.salon.entity.account.User;

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
