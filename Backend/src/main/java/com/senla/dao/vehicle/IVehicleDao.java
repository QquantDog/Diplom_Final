package com.senla.dao.vehicle;

import com.senla.model.vehicle.Vehicle;
import com.senla.util.dao.GenericDao;

import java.util.List;
import java.util.Optional;

public interface IVehicleDao extends GenericDao<Vehicle, Long> {
    List<Vehicle> findWithBrands();
    Optional<Vehicle> findWithBrandById(Long vehicleId);
}
