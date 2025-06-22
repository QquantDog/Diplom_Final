package com.senla.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.dto.cab.CabListWOCompanyResponseDto;
import com.senla.dto.pagination.PaginationRequest;
import com.senla.dto.taxicompany.*;
import com.senla.exception.DaoException;
import com.senla.exception.NotFoundByIdException;
import com.senla.model.cab.Cab;
import com.senla.model.taxicompany.TaxiCompany;
import com.senla.service.taxicompany.TaxiCompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class TaxiCompanyController {

    private final TaxiCompanyService taxiCompanyService;
    private final ModelMapper modelMapper;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority({'TC_READ_ALL'})")
    public ResponseEntity<List<TaxiCompanyResponseDto>> getAllCompanies(TaxiCompanyFilterDto filterDto, PaginationRequest paginationRequest) throws JsonProcessingException {
        List<TaxiCompany> companies = taxiCompanyService.findAllWithFilterAndPagination(filterDto, paginationRequest);
        return new ResponseEntity<>(companies.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{companyId}")
    @PreAuthorize("hasAnyAuthority({'TC_READ_ALL'})")
    public ResponseEntity<TaxiCompanyResponseDto> getCompanyById(@PathVariable("companyId") Long companyId) throws JsonProcessingException, NotFoundByIdException {
        TaxiCompany company = taxiCompanyService.findById(companyId).orElseThrow(()->new DaoException("Company not found"));
        return new ResponseEntity<>(convertToResponseDto(company), HttpStatus.OK);
    }

    @GetMapping("/all/cabs")
    @PreAuthorize("hasAnyAuthority({'TC_READ_CABS'})")
    public ResponseEntity<List<TaxiCompanyWithCabsResponseDto>> getAllWithCabsCompanies() {
        List<TaxiCompany> companies = taxiCompanyService.getWithCabList();
        return new ResponseEntity<>(convertToResponseWithCabListDto(companies), HttpStatus.OK);
    }

    @GetMapping("/{companyId}/cabs")
    @PreAuthorize("hasAnyAuthority({'TC_READ_CABS'})")
    public ResponseEntity<TaxiCompanyWithCabsResponseDto> getCompanyWithCabsById(@PathVariable("companyId") Long companyId) {
        TaxiCompany company = taxiCompanyService.getCompanyWithCabs(companyId);
        return new ResponseEntity<>(convertToResponseWithCabDto(company), HttpStatus.OK);
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority({'TC_CREATE'})")
    public ResponseEntity<TaxiCompanyResponseDto> createCompany(@Valid @RequestBody TaxiCompanyCreateDto taxiCompanyCreateDto) {
        return new ResponseEntity<>(convertToResponseDto(taxiCompanyService.createTaxiCompany(taxiCompanyCreateDto)), HttpStatus.CREATED);
    }
//admin
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority({'TC_UPD'})")
    public ResponseEntity<TaxiCompanyResponseDto> updateCompany(@Valid @RequestBody TaxiCompanyUpdateDto taxiCompanyUpdateDto, @PathVariable("id") Long id) throws JsonProcessingException {
        return new ResponseEntity<>(convertToResponseDto(taxiCompanyService.updateTaxiCompany(id, taxiCompanyUpdateDto)), HttpStatus.CREATED);
    }

//    admin
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority({'TC_DEL'})")
    public ResponseEntity<?> deleteCompany(@PathVariable("id") Long id) {
        taxiCompanyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private TaxiCompanyResponseDto convertToResponseDto(TaxiCompany taxiCompany){
        return modelMapper.map(taxiCompany, TaxiCompanyResponseDto.class);
    }


    private CabListWOCompanyResponseDto convertToCabList(Cab cab){
        return modelMapper.map(cab, CabListWOCompanyResponseDto.class);
    }

    private TaxiCompanyWithCabsResponseDto convertToResponseWithCabDto(TaxiCompany taxiCompany){
        TaxiCompanyWithCabsResponseDto dto = modelMapper.map(taxiCompany, TaxiCompanyWithCabsResponseDto.class);
        dto.setCabsResponseList(taxiCompany.getCabs().stream().map(this::convertToCabList).collect(Collectors.toList()));
        return dto;
    }

    private List<TaxiCompanyWithCabsResponseDto> convertToResponseWithCabListDto(List<TaxiCompany> taxiCompanies){
        List<TaxiCompanyWithCabsResponseDto> dtoArr = new ArrayList<>();
        for (TaxiCompany taxiCompany : taxiCompanies) {
            dtoArr.add(convertToResponseWithCabDto(taxiCompany));
        }
        return dtoArr;
    }
}
