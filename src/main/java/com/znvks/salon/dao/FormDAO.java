package com.znvks.salon.dao;

import com.znvks.salon.entity.Condition;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.account.Account;

import java.util.List;

public interface FormDAO extends BaseDAO<Long, Form> {

    List<Form> getFormsByAcc(Account account);

    List<Form> getFormsByCondition(Condition condition);
}
