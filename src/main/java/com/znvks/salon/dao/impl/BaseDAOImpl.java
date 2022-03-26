package com.znvks.salon.dao.impl;

import com.znvks.salon.dao.BaseDAO;
import com.znvks.salon.entity.BaseEntity;
import lombok.Getter;
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
        return Optional.ofNullable(session.find(clazz, id));
    }

    @Override
    public PK save(E entity) {
        Session session = sessionFactory.getCurrentSession();
        Serializable id = session.save(entity);
        return (PK) id;
    }

    @Override
    public void update(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(entity);
    }

    @Override
    public void delete(E entity) {
        if (Objects.nonNull(entity)) {
            Session session = sessionFactory.getCurrentSession();
            Object persistentInstance = session.load(clazz, entity.getId());
            if (persistentInstance != null) {
                session.delete(persistentInstance);
            }
        }
    }

    @Override
    public List<E> getAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<E> criteria = cb.createQuery(clazz);
        Root<E> root = criteria.from(clazz);
        return session.createQuery(criteria.select(root)).getResultList();
    }
}
