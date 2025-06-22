package com.senla.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.dto.vehicle.VehicleCreateDto;
import com.senla.dto.vehicle.VehicleFullResponseDto;
import com.senla.dto.vehicle.VehicleUpdateDto;
import com.senla.model.vehicle.Vehicle;
import com.senla.service.vehicle.VehicleService;
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
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;
    private final ModelMapper modelMapper;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority({'VEHICLE_READ_ALL'})")
    public ResponseEntity<List<VehicleFullResponseDto>> getAllVehicles() throws JsonProcessingException {
        List<Vehicle> vehicles = vehicleService.findWithBrands();
        return new ResponseEntity<>(vehicles.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{vehicleId}")
    @PreAuthorize("hasAnyAuthority({'VEHICLE_READ_ALL'})")
    public ResponseEntity<VehicleFullResponseDto> getVehicleById(@PathVariable("vehicleId") Long vehicleId) throws JsonProcessingException {
        Vehicle vehicle = vehicleService.findWithBrandById(vehicleId);
        return new ResponseEntity<>(convertToResponseDto(vehicle), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority({'VEHICLE_CRUD'})")
    public ResponseEntity<VehicleFullResponseDto> createVehicle(@Valid @RequestBody VehicleCreateDto vehicleCreateDto) throws JsonProcessingException {
        Vehicle vehicle = vehicleService.createVehicle(vehicleCreateDto);
        return new ResponseEntity<>(convertToResponseDto(vehicle), HttpStatus.CREATED);
    }

    @PutMapping("/{vehicleId}")
    @PreAuthorize("hasAnyAuthority({'VEHICLE_CRUD'})")
    public ResponseEntity<VehicleFullResponseDto> updateVehicle(@Valid @RequestBody VehicleUpdateDto vehicleUpdateDto, @PathVariable("vehicleId") Long vehicleId) throws JsonProcessingException {
        Vehicle vehicle = vehicleService.updateVehicle(vehicleId, vehicleUpdateDto);
        return new ResponseEntity<>(convertToResponseDto(vehicle), HttpStatus.CREATED);
    }

    @DeleteMapping("/{vehicleId}")
    @PreAuthorize("hasAnyAuthority({'VEHICLE_CRUD'})")
    public ResponseEntity<?> deleteVehicle(@PathVariable("vehicleId") Long vehicleId) {
        vehicleService.deleteById(vehicleId);
        return ResponseEntity.noContent().build();
    }

    private VehicleFullResponseDto convertToResponseDto(Vehicle vehicle){
        return modelMapper.map(vehicle, VehicleFullResponseDto.class);
    }
}
