package com.znvks.salon.service;

import com.znvks.salon.dto.AccountDTO;
import com.znvks.salon.dto.FormDTO;
import com.znvks.salon.dto.PetDTO;
import com.znvks.salon.entity.Condition;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.account.Account;

import java.util.List;
import java.util.Optional;

public interface FormService extends BaseService<FormDTO>{

    List<FormDTO> getFormsByAcc(AccountDTO account);

    List<FormDTO> getFormsByCondition(Condition condition);
}
