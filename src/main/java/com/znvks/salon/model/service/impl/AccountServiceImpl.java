package com.znvks.salon.model.service.impl;

import com.znvks.salon.model.dao.AccountDAO;
import com.znvks.salon.model.dto.AccountDTO;
import com.znvks.salon.model.entity.account.Account;
import com.znvks.salon.model.entity.account.Admin;
import com.znvks.salon.model.entity.account.User;
import com.znvks.salon.model.service.AccountService;
import com.znvks.salon.model.mapper.AccountMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO;
    private final AccountMapper accountMapper;

    @Override
    public Optional<AccountDTO> getById(Long id) {
        Optional<Account> account = accountDAO.getById(id);
        return Optional.ofNullable(accountMapper.mapToDto(account.orElse(null)));
    }

    @Override
    @Transactional
    public Long save(AccountDTO accountDTO) {
        return accountDAO.save(accountMapper.mapToEntity(accountDTO));
    }

    @Override
    @Transactional
    public void update(AccountDTO accountDTO) {
        accountDAO.update(accountMapper.mapToEntity(accountDTO));
    }

    @Override
    @Transactional
    public void delete(AccountDTO accountDTO) {
        Account account = accountDAO.getById(accountDTO.getId()).orElse(null);
        accountDAO.delete(account);
    }

    @Override
    public List<AccountDTO> getAll() {
        List<Account> accounts = accountDAO.getAll();
        return accountMapper.mapToListDto(accounts);
    }

    @Override
    public List<AccountDTO> getAllUsers() {
        List<User> accounts = accountDAO.getAllUsers();
        return accountMapper.toUserListDto(accounts);
    }

    @Override
    public List<AccountDTO> getAllAdmins() {
        List<Admin> accounts = accountDAO.getAllAdmins();
        return accountMapper.toAdminListDto(accounts);
    }

    @Override
    public Optional<AccountDTO> getAccByUsername(String username) {
        Optional<Account> account = accountDAO.getAccByUsername(username);
        return Optional.ofNullable(accountMapper.mapToDto(account.orElse(null)));
    }

    @Override
    public List<AccountDTO> getAccByName(String name) {
        List<User> accounts = accountDAO.getAccByName(name);
        return accountMapper.toUserListDto(accounts);
    }

    @Override
    public List<AccountDTO> getAccBySurname(String surname) {
        List<User> accounts = accountDAO.getAccBySurname(surname);
        return accountMapper.toUserListDto(accounts);
    }

    @Override
    public boolean isAuthenticate(AccountDTO accountDTO) {
        return accountDAO.isAuthenticate(accountMapper.mapToEntity(accountDTO));
    }

}
