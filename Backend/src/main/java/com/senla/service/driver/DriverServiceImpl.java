package com.senla.service.driver;

import com.senla.dao.driver.DriverDao;
import com.senla.dto.driver.UpdatePositionDto;
import com.senla.exception.DaoException;
import com.senla.model.driver.Driver;
import com.senla.util.sec.SecUtils;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class DriverServiceImpl extends AbstractLongIdGenericService<Driver> implements DriverService {

    @Autowired
    private DriverDao driverDao;

    @PostConstruct
    @Override
    public void init() {
        super.abstractDao = driverDao;
    }

    @Override
    @Transactional
    public Driver updatePosition(UpdatePositionDto updatePositionDto) {
        Driver driver = driverDao.findById(SecUtils.extractId()).orElseThrow(()->new DaoException("Driver not found"));
        if(!driver.getIsOnShift()) throw new DaoException("Driver is not on shift to update position");

        driver.setCurrentPoint(updatePositionDto.getCurrentPoint());
        return driverDao.update(driver);
    }

    @Override
    public Driver findOwnDetails() {
        return driverDao.findById(SecUtils.extractId()).orElseThrow(()->new DaoException("Driver not found"));
    }

}
