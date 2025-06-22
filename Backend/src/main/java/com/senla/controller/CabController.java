package com.senla.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.dao.cab.CabDao;
import com.senla.dto.cab.*;
import com.senla.dto.pagination.PaginationRequest;
import com.senla.exception.DaoException;
import com.senla.exception.NotFoundByIdException;
import com.senla.model.cab.Cab;
import com.senla.service.cab.CabService;
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
@RequestMapping("/cabs")
@RequiredArgsConstructor
public class CabController {

    private final CabService cabService;
    private final ModelMapper modelMapper;
    private final CabDao cabDao;

    @GetMapping("/search")
    @PreAuthorize("hasAuthority({'CAB_CRUD_ADMIN'})")
    public ResponseEntity<List<CabListResponseDto>> getAllCabs(@Valid CabFilterDto cabFilterDto, @Valid PaginationRequest paginationRequest) throws JsonProcessingException {
        List<Cab> cabs = cabService.getAllWithFilterAndPagination(cabFilterDto, paginationRequest);
        return new ResponseEntity<>(cabs.stream().map(this::convertToListResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/registered")
    @PreAuthorize("hasAuthority({'CAB_READ_OWN'})")
    public ResponseEntity<List<CabResponseDto>> getRegistered() throws JsonProcessingException {
        List<Cab> cabs = cabService.getRegisteredCabs();
        return new ResponseEntity<>(cabs.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/possible")
    @PreAuthorize("hasAuthority({'CAB_READ_OWN'})")
    public ResponseEntity<List<CabFullResponseDto>> getPossibleCabs() throws JsonProcessingException {
        List<Cab> cabs = cabService.getPossibleCabs();
        return new ResponseEntity<>(cabs.stream().map(this::convertToResponseFullDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{cabId}")
    @PreAuthorize("hasAuthority({'CAB_CRUD_ADMIN'})")
    public ResponseEntity<CabResponseDto> getCabById(@PathVariable("cabId") Long cabId) throws JsonProcessingException, NotFoundByIdException {
        Cab cab = cabDao.findById(cabId).orElseThrow(()->new DaoException("cab not found"));
        return new ResponseEntity<>(convertToResponseDto(cab), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority({'CAB_CRUD_ADMIN'})")
    public ResponseEntity<CabFullResponseDto> registerCab(@Valid @RequestBody CabCreateDto cabCreateDto) throws JsonProcessingException {
        Cab cab = cabService.createCab(cabCreateDto);
        return new ResponseEntity<>(convertToResponseFullDto(cab), HttpStatus.CREATED);
    }

    @PutMapping("/{cabId}")
    @PreAuthorize("hasAuthority({'CAB_CRUD_ADMIN'})")
    public ResponseEntity<CabResponseDto> updateCab(@Valid @RequestBody CabUpdateDto cabUpdateDto, @PathVariable("cabId") Long cabId) throws JsonProcessingException {
        return new ResponseEntity<>(convertToResponseDto(cabService.updateCab(cabId, cabUpdateDto)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority({'CAB_CRUD_ADMIN'})")
    public ResponseEntity<?> deleteCab(@PathVariable("id") Long id) {
        cabService.deleteCab(id);
        return ResponseEntity.noContent().build();
    }

    private CabResponseDto convertToResponseDto(Cab cab){
        return modelMapper.map(cab, CabResponseDto.class);
    }

    private CabFullResponseDto convertToResponseFullDto(Cab cab){
        return modelMapper.map(cab, CabFullResponseDto.class);
    }
    private CabListResponseDto convertToListResponseDto(Cab cab){
        return modelMapper.map(cab, CabListResponseDto.class);
    }
}
