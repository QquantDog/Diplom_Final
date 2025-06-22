package com.senla.service.vehicle;

import com.senla.dao.tier.TierDao;
import com.senla.dao.vehicle.VehicleDao;
import com.senla.dao.vehiclebrand.VehicleBrandDao;
import com.senla.dto.vehicle.VehicleCreateDto;
import com.senla.dto.vehicle.VehicleUpdateDto;
import com.senla.exception.DaoException;
import com.senla.model.tier.Tier;
import com.senla.model.vehicle.Vehicle;
import com.senla.model.vehiclebrand.VehicleBrand;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class VehicleServiceImpl extends AbstractLongIdGenericService<Vehicle> implements VehicleService {

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private VehicleBrandDao vehicleBrandDao;

    @Autowired
    private TierDao rateTierDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @PostConstruct
    protected void init() {
        super.abstractDao = vehicleDao;
    }

    @Override
    @Transactional
    public Vehicle createVehicle(VehicleCreateDto dto) {
        VehicleBrand vb = vehicleBrandDao.findById(dto.getBrandId()).orElseThrow(()->new DaoException("Vehicle brand not found"));
        Tier rt = rateTierDao.findById(dto.getBrandId()).orElseThrow(()->new DaoException("vehicle rate tier not found"));

        Vehicle vehicle = modelMapper.map(dto, Vehicle.class);
        vehicle.setBrand(vb);
        vehicle.setTier(rt);

        return vehicleDao.create(vehicle);
    }

    @Override
    @Transactional
    public Vehicle updateVehicle(Long id, VehicleUpdateDto dto) {
        Vehicle vehicle = vehicleDao.findById(id).orElseThrow(()->new DaoException("Vehicle not found"));

        VehicleBrand vb = vehicleBrandDao.findById(dto.getBrandId()).orElseThrow(()->new DaoException("Vehicle brand not found"));
        Tier rt = rateTierDao.findById(dto.getBrandId()).orElseThrow(()->new DaoException("vehicle rate tier not found"));

        modelMapper.map(dto, vehicle);
        vehicle.setBrand(vb);
        vehicle.setTier(rt);

        return vehicleDao.update(vehicle);
    }

    @Override
    public List<Vehicle> findWithBrands() {
        return vehicleDao.findWithBrands();
    }

    @Override
    @Transactional
    public Vehicle findWithBrandById(Long vehicleId) {
        return vehicleDao.findWithBrandById(vehicleId)
                .orElseThrow(()->new DaoException("Vehicle not found"));
    }

}
