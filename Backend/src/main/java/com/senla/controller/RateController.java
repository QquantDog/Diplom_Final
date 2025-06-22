package com.senla.controller;

import com.senla.dto.pagination.PaginationRequest;
import com.senla.dto.rate.RateCreateDto;
import com.senla.dto.rate.RateFilterDto;
import com.senla.dto.rate.RateFullResponseDto;
import com.senla.dto.rate.RateUpdateDto;
import com.senla.exception.DaoException;
import com.senla.model.rate.Rate;
import com.senla.service.rate.RateService;
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
@RequestMapping("/rates")
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;
    private final ModelMapper modelMapper;

    @GetMapping("/all")
    public ResponseEntity<List<RateFullResponseDto>> getAllRates() {
        List<Rate> rates = rateService.getAll();
        return new ResponseEntity<>(rates.stream().map(this::convertToFullResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RateFullResponseDto>> getAllRatesFilter(@Valid RateFilterDto rateFilterDto, @Valid PaginationRequest paginationRequest){
        List<Rate> rates = rateService.getAllWithFilterAndPagination(rateFilterDto, paginationRequest);
        return new ResponseEntity<>(rates.stream().map(this::convertToFullResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{rateId}")
    public ResponseEntity<RateFullResponseDto> getRateById(@PathVariable("rateId") Long rateId) {
        Rate rate = this.rateService.getById(rateId).orElseThrow(()->new DaoException("rate not found"));
        return new ResponseEntity<>(convertToFullResponseDto(rate), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority({'VEHICLE_CRUD'})")
    public ResponseEntity<RateFullResponseDto> createRate(@Valid @RequestBody RateCreateDto rateCreateDto) {
        Rate rate = rateService.createRate(rateCreateDto);
        return new ResponseEntity<>(convertToFullResponseDto(rate), HttpStatus.CREATED);
    }

    @PutMapping("/{rateId}")
    @PreAuthorize("hasAnyAuthority({'VEHICLE_CRUD'})")
    public ResponseEntity<RateFullResponseDto> updateRate(@Valid @RequestBody RateUpdateDto rateUpdateDto, @PathVariable("rateId") Long rateId) {
        return new ResponseEntity<>(convertToFullResponseDto(rateService.updateRate(rateId, rateUpdateDto)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{rateId}")
    @PreAuthorize("hasAnyAuthority({'VEHICLE_CRUD'})")
    public ResponseEntity<?> deleteRate(@PathVariable("rateId") Long rateId) {
        rateService.deleteById(rateId);
        return ResponseEntity.noContent().build();
    }

    private RateFullResponseDto convertToFullResponseDto(Rate rate){
        return modelMapper.map(rate, RateFullResponseDto.class);
    }
}
