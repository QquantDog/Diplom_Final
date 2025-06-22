package com.senla.service.tier;


import com.senla.dto.tier.TierCreateDto;
import com.senla.dto.tier.TierUpdateDto;
import com.senla.model.tier.Tier;
import com.senla.util.service.GenericService;

public interface TierService extends GenericService<Tier, Long> {
    Tier createTier(TierCreateDto tierCreateDto);
    Tier updateTier(Long tierId, TierUpdateDto tierUpdateDto);
}