package com.senla.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.dto.city.CityCreateDto;
import com.senla.dto.city.CityResponseDto;
import com.senla.dto.city.CityUpdateDto;
import com.senla.exception.DaoException;
import com.senla.model.city.City;
import com.senla.service.city.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;
    private final ModelMapper modelMapper;

    @GetMapping("/all")
    public ResponseEntity<List<CityResponseDto>> getAllCities() throws JsonProcessingException {
        List<City> cities = cityService.findAll();
        return new ResponseEntity<>(cities.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }
    @GetMapping("/{cityId}")
    @PreAuthorize("hasAuthority({'CITY_CRUD'})")
    public ResponseEntity<CityResponseDto> getCityById(@PathVariable("cityId") Long cityId) throws JsonProcessingException {
        City city = cityService.findById(cityId).orElseThrow(()->new DaoException("City not found"));
        return new ResponseEntity<>(convertToResponseDto(city), HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority({'CITY_CRUD'})")
    public ResponseEntity<CityResponseDto> createCity(@Valid @RequestBody CityCreateDto cityCreateDto) throws JsonProcessingException {
        City city = cityService.createCity(cityCreateDto);
        return new ResponseEntity<>(convertToResponseDto(city), HttpStatus.CREATED);
    }

    @PutMapping("/{cityId}")
    @PreAuthorize("hasAuthority({'CITY_CRUD'})")
    public ResponseEntity<CityResponseDto> updateCity(@Valid @RequestBody CityUpdateDto cityUpdateDto, @PathVariable("cityId") Long cityId) throws JsonProcessingException {
        City city = cityService.updateCity(cityId, cityUpdateDto);
        return new ResponseEntity<>(convertToResponseDto(city), HttpStatus.CREATED);
    }

    @DeleteMapping("/{cityId}")
    @PreAuthorize("hasAuthority({'CITY_CRUD'})")
    public ResponseEntity<?> deleteCity(@PathVariable("cityId") Long cityId) {
        cityService.deleteById(cityId);
        return ResponseEntity.noContent().build();
    }

    private CityResponseDto convertToResponseDto(City city) {
        return modelMapper.map(city, CityResponseDto.class);
    }
}
