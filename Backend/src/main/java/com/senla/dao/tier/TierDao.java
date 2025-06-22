package com.senla.dao.tier;

import com.senla.model.tier.Tier;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class TierDao extends AbstractLongDao<Tier> implements ITierDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = Tier.class;
    }

    @Override
    public Tier findTierForCab(Long cabId) {
        TypedQuery<Tier> q = em.createQuery( """
                                         select r from Tier r
                                         join r.vehicles v
                                         join v.cabs c
                                         where c.cabId=:cabId
                                         """ , Tier.class);
        q.setParameter("cabId", cabId);
        return q.getSingleResult();
    }
}
