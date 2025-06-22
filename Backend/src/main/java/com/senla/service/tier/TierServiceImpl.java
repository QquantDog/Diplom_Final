package com.senla.service.tier;

import com.senla.dao.tier.TierDao;
import com.senla.dto.tier.TierCreateDto;
import com.senla.dto.tier.TierUpdateDto;
import com.senla.exception.DaoException;
import com.senla.model.tier.Tier;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TierServiceImpl extends AbstractLongIdGenericService<Tier> implements TierService {

    @Autowired
    private TierDao tierDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @PostConstruct
    protected void init() {
        super.abstractDao = tierDao;
    }


    @Override
    @Transactional
    public Tier createTier(TierCreateDto tierCreateDto) {
        Tier tier = modelMapper.map(tierCreateDto, Tier.class);
        return tierDao.create(tier);
    }

    @Override
    @Transactional
    public Tier updateTier(Long tierId, TierUpdateDto tierUpdateDto) {
        Tier tier = tierDao.findById(tierId).orElseThrow(()->new DaoException("Tier not found"));
        modelMapper.map(tierUpdateDto, tier);
        return tierDao.update(tier);
    }
}
