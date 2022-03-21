package com.znvks.salon.mapper;

import com.znvks.salon.dto.AccountDTO;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.Admin;
import com.znvks.salon.entity.account.User;
import org.mapstruct.BeforeMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {PetMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AccountMapper {

    AccountDTO toDto(User account);

    AccountDTO toDto(Admin account);

    List<AccountDTO> toAdminListDto(List<Admin> admins);

    List<AccountDTO> toUserListDto(List<User> users);

    default AccountDTO mapToDto(Account account){
        AccountDTO result = null;
        if (account instanceof User) {
           result = toDto((User) account);
        } else if (account instanceof Admin) {
           result = toDto((Admin) account);
        }
        return result;
    }

    default List<AccountDTO> mapToListDto(List<Account> accounts){
        List<AccountDTO> dtos = new ArrayList<>();
        for(Account account: accounts){
            dtos.add(mapToDto(account));
        }
        return dtos;
    }

}
