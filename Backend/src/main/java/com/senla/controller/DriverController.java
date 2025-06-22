package com.senla.controller;

import com.senla.dto.driver.DriverResponseDto;
import com.senla.dto.driver.UpdatePositionDto;
import com.senla.exception.DaoException;
import com.senla.model.driver.Driver;
import com.senla.service.driver.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasAuthority({'DRIV_READ_ALL'})")
    @GetMapping
    public ResponseEntity<List<DriverResponseDto>> getAllDriverPositions() {
        Collection<Driver> drivers = this.driverService.findAll();
        return new ResponseEntity<>(drivers.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority({'DRIV_READ_ALL'})")
    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDto> getDriverPositionById(@PathVariable("id") Long id) {
        Driver driver = driverService.findById(id).orElseThrow(()->new DaoException("Driver not found"));
        return new ResponseEntity<>(convertToResponseDto(driver), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority({'DRIV_READ_OWN'})")
    @GetMapping("/details")
    public ResponseEntity<DriverResponseDto> getOwnDetails() {
        Driver driver = driverService.findOwnDetails();
        return new ResponseEntity<>(convertToResponseDto(driver), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority({'DRIV_UPD_POS'})")
    public ResponseEntity<DriverResponseDto> updateOwnPosition(@Valid @RequestBody UpdatePositionDto updatePositionDto){
        Driver driver = driverService.updatePosition(updatePositionDto);
        return new ResponseEntity<>(convertToResponseDto(driver), HttpStatus.CREATED);
    }

    private DriverResponseDto convertToResponseDto(Driver driver) {
        return modelMapper.map(driver, DriverResponseDto.class);
    }
}
