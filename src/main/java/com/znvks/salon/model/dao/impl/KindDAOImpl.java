package com.znvks.salon.model.dao.impl;

import com.znvks.salon.model.dao.KindDAO;
import com.znvks.salon.model.entity.Kind;
import com.znvks.salon.model.entity.Kind_;
import com.znvks.salon.model.util.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Slf4j
@Repository
public class KindDAOImpl extends BaseDAOImpl<Long, Kind> implements KindDAO {

    @Override
    public Optional<Kind> getByName(String name) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Kind> criteria = cb.createQuery(Kind.class);
        Root<Kind> root = criteria.from(Kind.class);
        criteria.select(root).where(cb.equal(root.get(Kind_.kind), name));
        final Optional<Kind> kind = session.createQuery(criteria).getResultStream().findFirst();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, kind, name);
        return kind;
    }
}
