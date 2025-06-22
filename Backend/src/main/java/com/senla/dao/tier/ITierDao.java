package com.senla.dao.tier;

import com.senla.model.tier.Tier;
import com.senla.util.dao.GenericDao;

public interface ITierDao extends GenericDao<Tier, Long> {
    Tier findTierForCab(Long cabId);
}
