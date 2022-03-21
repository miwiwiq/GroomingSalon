package com.znvks.salon.service.impl;

import com.znvks.salon.dao.AccountDAO;
import com.znvks.salon.dao.ServiceDAO;
import com.znvks.salon.dto.AccountDTO;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.Admin;
import com.znvks.salon.entity.account.User;
import com.znvks.salon.mapper.AccountMapper;

import com.znvks.salon.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Transactional
    public Long save(Account account) {
        return accountDAO.save(account);
    }

    @Override
    public void update(Account account) {
        accountDAO.update(account);
    }

    @Override
    public void delete(Account account) {
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
    public boolean isAuthenticate(Account account) {
        return accountDAO.isAuthenticate(account);
    }

}
