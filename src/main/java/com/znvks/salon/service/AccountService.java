package com.znvks.salon.service;

import com.znvks.salon.dto.AccountDTO;
import com.znvks.salon.dto.ServiceDTO;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Service;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.Admin;
import com.znvks.salon.entity.account.User;

import java.util.List;
import java.util.Optional;

public interface AccountService extends BaseService<AccountDTO> {

    List<AccountDTO> getAllUsers();

    List<AccountDTO> getAllAdmins();

    Optional<AccountDTO> getAccByUsername(String username);

    List<AccountDTO> getAccByName(String name);

    List<AccountDTO> getAccBySurname(String surname);

    boolean isAuthenticate(AccountDTO account);

}
