package com.senla.service.rate;

import com.senla.dao.city.CityDao;
import com.senla.dao.rate.RateDao;
import com.senla.dao.tier.TierDao;
import com.senla.dto.pagination.PaginationRequest;
import com.senla.dto.rate.RateCreateDto;
import com.senla.dto.rate.RateFilterDto;
import com.senla.dto.rate.RateUpdateDto;
import com.senla.exception.DaoException;
import com.senla.exception.PaginationException;
import com.senla.model.city.City;
import com.senla.model.rate.Rate;
import com.senla.model.tier.Tier;
import com.senla.specification.RateSpecification;
import com.senla.util.pagination.PaginationUtils;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class RateServiceImpl extends AbstractLongIdGenericService<Rate> implements RateService {
    @Autowired
    private RateDao rateDao;
    @Autowired
    private CityDao cityDao;


    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TierDao tierDao;


    @PostConstruct
    @Override
    public void init() {
        super.abstractDao = rateDao;
    }


    @Override
    public List<Rate> getAll(){
        return rateDao.getRatesFull();
    }

    @Override
    @Transactional
    public Optional<Rate> getById(Long rateId) {
        return rateDao.getRateById(rateId);
    }

    @Override
    public List<Rate> getAllWithFilterAndPagination(RateFilterDto rateFilterDto, PaginationRequest paginationRequest) {
        return rateDao.getAllWithFilterAndPagination(RateSpecification.buildSpecification(rateFilterDto), PaginationUtils
                .getOffsetByCountAndParams((int) rateDao.count(), paginationRequest)
                    .orElseThrow(PaginationException::new));
    }


    @Override
    @Transactional
    public Rate createRate(RateCreateDto dto) {
        City city = cityDao.findById(dto.getCityId()).orElseThrow(()->new DaoException("City not found"));
        Tier tier = tierDao.findById(dto.getTierId()).orElseThrow(()->new DaoException("Tier not found"));

        Rate rate = modelMapper.map(dto, Rate.class);
        rate.setCity(city);
        rate.setTier(tier);

        return abstractDao.create(rate);
    }

    @Override
    @Transactional
    public Rate updateRate(Long rateId, RateUpdateDto dto) {

        Rate rate = rateDao.getRateById(rateId).orElseThrow(()->new DaoException("Rate not found"));
        City city = cityDao.findById(dto.getCityId()).orElseThrow(()->new DaoException("City not found"));
        Tier tier = tierDao.findById(dto.getTierId()).orElseThrow(()->new DaoException("Tier not found"));

        modelMapper.map(dto, rate);
        rate.setCity(city);
        rate.setTier(tier);

        return rateDao.update(rate);
    }
}
