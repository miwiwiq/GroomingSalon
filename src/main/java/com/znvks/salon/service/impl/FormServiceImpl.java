package com.znvks.salon.service.impl;

import com.znvks.salon.dao.AccountDAO;
import com.znvks.salon.dao.FormDAO;
import com.znvks.salon.dto.AccountDTO;
import com.znvks.salon.dto.FormDTO;
import com.znvks.salon.entity.Condition;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.mapper.AccountMapper;
import com.znvks.salon.mapper.FormMapper;
import com.znvks.salon.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class FormServiceImpl implements FormService {

    private final FormDAO formDAO;
    private final FormMapper formMapper;
    private final AccountMapper accountMapper;


    @Override
    public Optional<FormDTO> getById(Long id) {
        Optional<Form> form = formDAO.getById(id);
        return Optional.ofNullable(formMapper.mapToDto(form.orElse(null)));
    }

    @Override
    @Transactional
    public Long save(FormDTO form) {
        return formDAO.save(formMapper.mapToEntity(form));
    }

    @Override
    @Transactional
    public void update(FormDTO form) {
        formDAO.update(formMapper.mapToEntity(form));
    }

    @Override
    @Transactional
    public void delete(FormDTO formDTO) {
        Form form = formDAO.getById(formDTO.getId()).orElse(null);
        formDAO.delete(form);
    }

    @Override
    public List<FormDTO> getAll() {
        List<Form> forms = formDAO.getAll();
        return formMapper.mapToListDto(forms);
    }

    @Override
    public List<FormDTO> getFormsByAcc(AccountDTO account) {
        List<Form> forms = formDAO.getFormsByAcc(accountMapper.mapToEntity(account));
        return formMapper.mapToListDto(forms);
    }

    @Override
    public List<FormDTO> getFormsByCondition(Condition condition) {
        List<Form> forms = formDAO.getFormsByCondition(condition);
        return formMapper.mapToListDto(forms);
    }
}
