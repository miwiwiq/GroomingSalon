package com.znvks.salon.model.service;

import com.znvks.salon.model.dto.AccountDTO;

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
