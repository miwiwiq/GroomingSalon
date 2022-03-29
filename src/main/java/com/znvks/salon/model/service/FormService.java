package com.znvks.salon.model.service;

import com.znvks.salon.model.dto.AccountDTO;
import com.znvks.salon.model.dto.FormDTO;
import com.znvks.salon.model.entity.Condition;

import java.util.List;

public interface FormService extends BaseService<FormDTO>{

    List<FormDTO> getFormsByAcc(AccountDTO account);

    List<FormDTO> getFormsByCondition(Condition condition);
}
