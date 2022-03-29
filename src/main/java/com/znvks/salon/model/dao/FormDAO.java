package com.znvks.salon.model.dao;

import com.znvks.salon.model.entity.Condition;
import com.znvks.salon.model.entity.Form;
import com.znvks.salon.model.entity.account.Account;

import java.util.List;

public interface FormDAO extends BaseDAO<Long, Form> {

    List<Form> getFormsByAcc(Account account);

    List<Form> getFormsByCondition(Condition condition);
}
