package com.senla.service.cab;

import com.senla.dao.cab.CabDao;
import com.senla.dao.taxicompany.TaxiCompanyDao;
import com.senla.dao.vehicle.VehicleDao;
import com.senla.dto.cab.CabCreateDto;
import com.senla.dto.cab.CabFilterDto;
import com.senla.dto.cab.CabUpdateDto;
import com.senla.dto.pagination.PaginationRequest;
import com.senla.exception.DaoException;
import com.senla.exception.PaginationException;
import com.senla.model.cab.Cab;
import com.senla.model.taxicompany.TaxiCompany;
import com.senla.model.vehicle.Vehicle;
import com.senla.repository.cab.CabRepository;
import com.senla.specification.CabSpecification;
import com.senla.util.pagination.PaginationUtils;
import com.senla.util.sec.SecUtils;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CabServiceImpl implements CabService {

    @Autowired
    private CabRepository cabRepository;

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private TaxiCompanyDao taxiCompanyDao;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Transactional()
    public Cab createCab(CabCreateDto dto) {
        Optional<Vehicle> vehicleResp = vehicleDao.findById(dto.getVehicleId());
        if(vehicleResp.isEmpty()) throw new DaoException("Vehicle not found");

        Optional<TaxiCompany> taxiCompanyResp = taxiCompanyDao.findById(dto.getTaxiCompanyId());
        if(taxiCompanyResp.isEmpty()) throw new DaoException("Taxi company not found");

        Vehicle vehicle = vehicleResp.get();
        TaxiCompany taxiCompany = taxiCompanyResp.get();

        Cab cab = modelMapper.map(dto, Cab.class);
        cab.setIsOnShift(false);
        cab.setVehicle(vehicle);
        cab.setCompany(taxiCompany);

        return cabRepository.save(cab);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Cab updateCab(Long id, CabUpdateDto dto) {
        Optional<Cab> cabResp = cabRepository.findById(id);
        if (cabResp.isEmpty()) throw new DaoException("Can't find entity with id " + id);

        Cab cabToUpdate = cabResp.get();
        Boolean isOnShiftSaved = cabToUpdate.getIsOnShift();
        modelMapper.map(dto, cabToUpdate);
        cabToUpdate.setIsOnShift(isOnShiftSaved);

        if(!cabToUpdate.getVehicle().getVehicleId().equals(dto.getVehicleId())) {
            Optional<Vehicle> vehicleResp = vehicleDao.findById(dto.getVehicleId());
            vehicleResp.ifPresentOrElse(cabToUpdate::setVehicle, ()->{throw new DaoException("Vehicle not found");});
        }
        if(!cabToUpdate.getCompany().getCompanyId().equals(dto.getTaxiCompanyId())) {
            Optional<TaxiCompany> taxiCompanyResp = taxiCompanyDao.findById(dto.getTaxiCompanyId());
            taxiCompanyResp.ifPresentOrElse(cabToUpdate::setCompany, ()->{throw new DaoException("Taxi company not found");});
        }
        return cabRepository.save(cabToUpdate);
    }

    @Override
    public List<Cab> getAll() {
        return cabRepository.getAllCabs();
    }

    @Override
    @Transactional
    public List<Cab> getRegisteredCabs() {
        return cabRepository.getRegisteredCabs(SecUtils.extractId());
    }

    @Override
    @Transactional
    public List<Cab> getPossibleCabs() {
        return cabRepository.getPossibleCabs(SecUtils.extractId());
    }

    @Override
    public List<Cab> getAllWithFilterAndPagination(CabFilterDto cabFilterDto, PaginationRequest paginationRequest) {
        return cabRepository.findWithFilterAndPagination(CabSpecification.buildSpecification(cabFilterDto), PaginationUtils
                .getOffsetByCountAndParams((int) cabRepository.count(), paginationRequest)
                    .orElseThrow(PaginationException::new));
    }

    @Override
    public void deleteCab(Long id) {
        cabRepository.deleteById(id);
    }
}
