package com.senla.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.dto.driverregistry.DriverRegistryCreateDto;
import com.senla.dto.driverregistry.DriverRegistryFullResponseDto;
import com.senla.dto.driverregistry.DriverRegistryWithCompanyResponseDto;
import com.senla.dto.pagination.PaginationRequest;
import com.senla.exception.NotFoundByIdException;
import com.senla.model.driverregistry.DriverRegistry;
import com.senla.service.driverregistry.DriverRegistryService;
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
@RequestMapping("/registry")
@RequiredArgsConstructor
public class DriverRegistryController {

    private final DriverRegistryService driverRegistryService;
    private final ModelMapper modelMapper;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority({'DRIVREG_READ_ALL'})")
    public ResponseEntity<List<DriverRegistryFullResponseDto>> getAllEntries(PaginationRequest paginationRequest) throws JsonProcessingException {
        List<DriverRegistry> entries = driverRegistryService.findAllEntries(paginationRequest);
        return new ResponseEntity<>(entries.stream().map(this::convertToFullResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority({'DRIVREG_READ_OWN'})")
    public ResponseEntity<List<DriverRegistryWithCompanyResponseDto>> getEntriesForDriver() throws JsonProcessingException, NotFoundByIdException {
        List<DriverRegistry> driverRegistry = this.driverRegistryService.findDriverCompanies();
        return new ResponseEntity<>(driverRegistry.stream().map(this::convertToCompanyResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }


    @PostMapping("/register")
    @PreAuthorize("hasAuthority({'DRIVREG_CREATE_REG'})")
    public ResponseEntity<DriverRegistryWithCompanyResponseDto> registerDriverInCompany(@Valid @RequestBody DriverRegistryCreateDto driverRegistryCreateDto) throws JsonProcessingException {
        DriverRegistry driverRegistry = driverRegistryService.registerDriverId(driverRegistryCreateDto);
        return new ResponseEntity<>(this.convertToCompanyResponseDto(driverRegistry), HttpStatus.CREATED);
    }


    @DeleteMapping("/{companyId}")
    @PreAuthorize("hasAuthority({'DRIVREG_DEL_REG'})")
    public ResponseEntity<?> deleteEntry(@PathVariable("companyId") Long companyId) {
        driverRegistryService.deleteDriverRegistryEntry(companyId);
        return ResponseEntity.noContent().build();
    }

    private DriverRegistryWithCompanyResponseDto convertToCompanyResponseDto(DriverRegistry driverRegistry) {
        return modelMapper.map(driverRegistry, DriverRegistryWithCompanyResponseDto.class);
    }

    private DriverRegistryFullResponseDto convertToFullResponseDto(DriverRegistry driverRegistry) {
        return modelMapper.map(driverRegistry, DriverRegistryFullResponseDto.class);
    }
}
