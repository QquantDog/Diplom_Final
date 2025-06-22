package com.senla.dao.driver;

import com.senla.model.driver.Driver;
import com.senla.util.dao.GenericDao;

import java.util.Optional;

public interface IDriverDao extends GenericDao<Driver, Long> {
    Driver bindToUser(Long userId);
    Driver getDriverWithCompanies(Long driverId);
    Optional<Driver> findDriverCabWithinCompanies(Long driverId, Long cabId);
}
