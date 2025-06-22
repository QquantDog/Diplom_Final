package com.senla.service.city;

import com.senla.dao.city.CityDao;
import com.senla.dto.city.CityCreateDto;
import com.senla.dto.city.CityUpdateDto;
import com.senla.exception.DaoException;
import com.senla.model.city.City;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class CityServiceImpl extends AbstractLongIdGenericService<City> implements CityService {

    @Autowired
    private CityDao cityDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @PostConstruct
    protected void init() {
        super.abstractDao = cityDao;
    }

    @Override
    @Transactional
    public City createCity(CityCreateDto cityCreateDto){
        City city = modelMapper.map(cityCreateDto, City.class);
        return cityDao.create(city);
    }

    @Override
    @Transactional
    public City updateCity(Long cityId, CityUpdateDto cityUpdateDto) {
        City city = cityDao.findById(cityId).orElseThrow(()->new DaoException("City not found"));
        modelMapper.map(cityUpdateDto, city);
        return cityDao.update(city);
    }
}
