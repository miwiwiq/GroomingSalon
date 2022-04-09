package com.znvks.salon.model.dao.impl;

import com.znvks.salon.model.dao.BaseDAO;
import com.znvks.salon.model.entity.BaseEntity;
import com.znvks.salon.model.util.LoggerUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Getter
public abstract class BaseDAOImpl<PK extends Serializable, E extends BaseEntity<PK>> implements BaseDAO<PK, E> {

    @Autowired
    private SessionFactory sessionFactory;
    private Class<E> clazz;

    protected BaseDAOImpl() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        clazz = (Class<E>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[1];
    }

    @Override
    public Optional<E> getById(PK id) {
        Session session = sessionFactory.getCurrentSession();
        E entity = session.find(clazz, id);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, entity, id);
        return Optional.ofNullable(entity);
    }

    @Override
    public PK save(E entity) {
        Session session = sessionFactory.getCurrentSession();
        Serializable id = session.save(entity);
        log.debug(LoggerUtil.ENTITY_WAS_SAVED_IN_DAO, entity);
        return (PK) id;
    }

    @Override
    public void update(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(entity);
        log.debug(LoggerUtil.ENTITY_WAS_UPDATED_IN_DAO, entity);
    }

    @Override
    public void delete(E entity) {
        if (Objects.nonNull(entity)) {
            Session session = sessionFactory.getCurrentSession();
            Object persistentInstance = session.load(clazz, entity.getId());
            if (persistentInstance != null) {
                session.delete(persistentInstance);
                log.debug(LoggerUtil.ENTITY_WAS_DELETED_IN_DAO, entity);
            }
        }
    }

    @Override
    public List<E> getAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<E> criteria = cb.createQuery(clazz);
        Root<E> root = criteria.from(clazz);
        List<E> resultList = session.createQuery(criteria.select(root)).getResultList();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO, resultList);
        return resultList;
    }
}
