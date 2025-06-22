package com.senla.service.city;


import com.senla.dto.city.CityCreateDto;
import com.senla.dto.city.CityUpdateDto;
import com.senla.model.city.City;
import com.senla.util.service.GenericService;

public interface CityService extends GenericService<City, Long> {
    City createCity(CityCreateDto cityCreateDto);
    City updateCity(Long cityId, CityUpdateDto cityUpdateDto);
}
