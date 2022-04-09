package com.znvks.salon.model.service.impl;

import com.znvks.salon.model.dao.FormDAO;
import com.znvks.salon.model.dto.AccountDTO;
import com.znvks.salon.model.dto.FormDTO;
import com.znvks.salon.model.entity.Condition;
import com.znvks.salon.model.entity.Form;
import com.znvks.salon.model.service.FormService;
import com.znvks.salon.model.mapper.AccountMapper;
import com.znvks.salon.model.mapper.FormMapper;
import com.znvks.salon.model.util.LoggerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
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
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, form, id);
        return Optional.ofNullable(formMapper.mapToDto(form.orElse(null)));
    }

    @Override
    @Transactional
    public Long save(FormDTO form) {
        Long id = formDAO.save(formMapper.mapToEntity(form));
        log.debug(LoggerUtil.ENTITY_WAS_SAVED_IN_SERVICE, id);
        return id;
    }

    @Override
    @Transactional
    public void update(FormDTO form) {
        formDAO.update(formMapper.mapToEntity(form));
        log.debug(LoggerUtil.ENTITY_WAS_UPDATED_IN_SERVICE, form);

    }

    @Override
    @Transactional
    public void delete(FormDTO formDTO) {
        Form form = formDAO.getById(formDTO.getId()).orElse(null);
        formDAO.delete(form);
        log.debug(LoggerUtil.ENTITY_WAS_DELETED_IN_SERVICE, formDTO);
    }

    @Override
    public List<FormDTO> getAll() {
        List<Form> forms = formDAO.getAll();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE, forms);
        return formMapper.mapToListDto(forms);
    }

    @Override
    public List<FormDTO> getFormsByAcc(AccountDTO account) {
        List<Form> forms = formDAO.getFormsByAcc(accountMapper.mapToEntity(account));
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, forms, account);
        return formMapper.mapToListDto(forms);
    }

    @Override
    public List<FormDTO> getFormsByCondition(Condition condition) {
        List<Form> forms = formDAO.getFormsByCondition(condition);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, forms, condition);
        return formMapper.mapToListDto(forms);
    }
}
