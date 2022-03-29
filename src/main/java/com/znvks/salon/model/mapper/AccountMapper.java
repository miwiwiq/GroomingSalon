package com.znvks.salon.model.mapper;

import com.znvks.salon.model.dao.AccountDAO;
import com.znvks.salon.model.dto.AccountDTO;
import com.znvks.salon.model.entity.account.Account;
import com.znvks.salon.model.entity.account.Admin;
import com.znvks.salon.model.entity.account.User;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring", uses = {PetMapper.class})
public abstract class AccountMapper {

    @Autowired
    AccountDAO accountDAO;

    abstract AccountDTO toDto(User account);

    abstract AccountDTO toDto(Admin account);

    public abstract List<AccountDTO> toAdminListDto(List<Admin> admins);

    public abstract List<AccountDTO> toUserListDto(List<User> users);

    public AccountDTO mapToDto(Account account) {
        AccountDTO result = null;
        if (account instanceof User) {
            result = toDto((User) account);
        } else if (account instanceof Admin) {
            result = toDto((Admin) account);
        }
        return result;
    }

    public List<AccountDTO> mapToListDto(List<Account> accounts) {
        List<AccountDTO> dtos = new ArrayList<>();
        for (Account account : accounts) {
            dtos.add(mapToDto(account));
        }
        return dtos;
    }

    abstract User toUserEntity(AccountDTO accountDTO);

    abstract Admin toAdminEntity(AccountDTO accountDTO);

    public Account mapToEntity(AccountDTO accountDTO) {
        Account account;
        if (Objects.nonNull(accountDTO.getId())) {
            account = accountDAO.getById(accountDTO.getId()).orElse(null);
            if (account instanceof Admin) {
                account = toAdminEntity(accountDTO);
                account.setId(accountDTO.getId());
            } else {
                account = toUserEntity(accountDTO);
                account.setId(accountDTO.getId());
            }
        } else {
            account = toUserEntity(accountDTO);
        }
        return account;
    }

}
