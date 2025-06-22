package com.senla.service.vehicle;

import com.senla.dto.vehicle.VehicleCreateDto;
import com.senla.dto.vehicle.VehicleUpdateDto;
import com.senla.model.vehicle.Vehicle;
import com.senla.util.service.GenericService;

import java.util.List;

public interface VehicleService extends GenericService<Vehicle, Long> {
    Vehicle createVehicle(VehicleCreateDto vehicleCreateDto);
    Vehicle updateVehicle(Long id, VehicleUpdateDto vehicleUpdateDto);

    List<Vehicle> findWithBrands();
    Vehicle findWithBrandById(Long vehicleId);
}
