package com.znvks.salon.dao.impl;

import com.znvks.salon.dao.KindDAO;
import com.znvks.salon.entity.Kind;
import com.znvks.salon.entity.Kind_;
import lombok.Cleanup;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class KindDAOImpl extends BaseDAOImpl<Long, Kind> implements KindDAO {

    @Override
    public Optional<Kind> getByName(String name) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Kind> criteria = cb.createQuery(Kind.class);
        Root<Kind> root = criteria.from(Kind.class);
        criteria.select(root).where(cb.equal(root.get(Kind_.kind), name));
        return Optional.ofNullable(session.createQuery(criteria).getSingleResult());
    }
}
