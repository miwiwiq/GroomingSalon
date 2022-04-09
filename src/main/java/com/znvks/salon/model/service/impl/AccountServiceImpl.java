package com.znvks.salon.model.service.impl;

import com.znvks.salon.model.dao.AccountDAO;
import com.znvks.salon.model.dto.AccountDTO;
import com.znvks.salon.model.entity.account.Account;
import com.znvks.salon.model.entity.account.Admin;
import com.znvks.salon.model.entity.account.User;
import com.znvks.salon.model.service.AccountService;
import com.znvks.salon.model.mapper.AccountMapper;

import com.znvks.salon.model.util.LoggerUtil;
import com.znvks.salon.web.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO;
    private final AccountMapper accountMapper;

    private final Function<Account, UserDetails> userToUserDetails = user ->
            org.springframework.security.core.userdetails.User
                    .builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(user.getRole())
                    .build();

    @Override
    public Optional<AccountDTO> getById(Long id) {
        Optional<Account> account = accountDAO.getById(id);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, account, id);
        return Optional.ofNullable(accountMapper.mapToDto(account.orElse(null)));
    }

    @Override
    @Transactional
    public Long save(AccountDTO accountDTO) {
        final Long id = accountDAO.save(accountMapper.mapToEntity(accountDTO));
        log.debug(LoggerUtil.ENTITY_WAS_SAVED_IN_SERVICE, id);
        return id;
    }

    @Override
    @Transactional
    public void update(AccountDTO accountDTO) {
        accountDAO.update(accountMapper.mapToEntity(accountDTO));
        log.debug(LoggerUtil.ENTITY_WAS_UPDATED_IN_SERVICE, accountDTO);
    }

    @Override
    @Transactional
    public void delete(AccountDTO accountDTO) {
        Account account = accountDAO.getById(accountDTO.getId()).orElse(null);
        accountDAO.delete(account);
        log.debug(LoggerUtil.ENTITY_WAS_DELETED_IN_SERVICE, accountDTO);
    }

    @Override
    public List<AccountDTO> getAll() {
        List<Account> accounts = accountDAO.getAll();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE, accounts);
        return accountMapper.mapToListDto(accounts);
    }

    @Override
    public List<AccountDTO> getAllUsers() {
        List<User> accounts = accountDAO.getAllUsers();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE, accounts);
        return accountMapper.toUserListDto(accounts);
    }

    @Override
    public List<AccountDTO> getAllAdmins() {
        List<Admin> accounts = accountDAO.getAllAdmins();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE, accounts);
        return accountMapper.toAdminListDto(accounts);
    }

    @Override
    public Optional<AccountDTO> getAccByUsername(String username) {
        Optional<Account> account = accountDAO.getAccByUsername(username);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, account, username);
        return Optional.ofNullable(accountMapper.mapToDto(account.orElse(null)));
    }

    @Override
    public List<AccountDTO> getAccByName(String name) {
        List<User> accounts = accountDAO.getAccByName(name);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, accounts, name);
        return accountMapper.toUserListDto(accounts);
    }

    @Override
    public List<AccountDTO> getAccBySurname(String surname) {
        List<User> accounts = accountDAO.getAccBySurname(surname);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, accounts, surname);
        return accountMapper.toUserListDto(accounts);
    }

    @Override
    public boolean isAuthenticate(AccountDTO accountDTO) {
        return accountDAO.isAuthenticate(accountMapper.mapToEntity(accountDTO));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> maybeUser = accountDAO.getAccByUsername(username);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, maybeUser, username);
        return maybeUser
                .map(userToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find user with username: " + username));
    }
}
