package com.senla.service.driverregistry;

import com.senla.dao.driver.DriverDao;
import com.senla.dao.driverregistry.DriverRegistryDao;
import com.senla.dao.taxicompany.TaxiCompanyDao;
import com.senla.dto.driverregistry.DriverRegistryCreateDto;
import com.senla.dto.pagination.PaginationRequest;
import com.senla.exception.DaoException;
import com.senla.exception.PaginationException;
import com.senla.model.driverregistry.DriverRegistry;
import com.senla.util.pagination.PaginationUtils;
import com.senla.util.sec.SecUtils;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
public class DriverRegistryServiceImpl extends AbstractLongIdGenericService<DriverRegistry> implements DriverRegistryService {

    @Autowired
    private DriverRegistryDao driverRegistryDao;

    @Autowired
    private TaxiCompanyDao taxiCompanyDao;

    @Autowired
    private DriverDao driverDao;
    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    @Override
    public void init() {
        super.abstractDao = driverRegistryDao;
    }

    @Override
    @Transactional
    public DriverRegistry registerDriverId(DriverRegistryCreateDto driverRegistryCreateDto) {

        Long driverId = SecUtils.extractId();
        Long companyId = driverRegistryCreateDto.getCompanyId();

        if(driverRegistryDao.getEntry(driverId, companyId).isPresent())
            throw new DaoException("Registry entry already exists");

        DriverRegistry dr = modelMapper.map(driverRegistryCreateDto, DriverRegistry.class);
        dr.setDriver(
                driverDao.findById(driverId)
                        .orElseThrow(()-> new DaoException("Driver not found"))
        );
        dr.setTaxiCompany(
                taxiCompanyDao.findById(companyId)
                        .orElseThrow(()-> new DaoException("Taxi company not found"))
        );
        dr.setRegistrationDate(LocalDate.now());

        return driverRegistryDao.create(dr);
    }

    @Override
    public List<DriverRegistry> findAllEntries(PaginationRequest paginationRequest) {
        return driverRegistryDao.findAllWithPagination(PaginationUtils
                .getOffsetByCountAndParams((int) driverRegistryDao.count(), paginationRequest)
                    .orElseThrow(PaginationException::new));
    }

    @Override
    public List<DriverRegistry> findDriverCompanies() {
        return driverRegistryDao.getByDriverId(SecUtils.extractId());
    }


    @Override
    @Transactional
    public void deleteDriverRegistryEntry(Long companyId){
        DriverRegistry dr = driverRegistryDao.getEntry(SecUtils.extractId(), companyId)
                .orElseThrow(()->new DaoException("driver registry entry not found"));
        driverRegistryDao.deleteById(dr.getId());
    }

}
