package com.senla.dao.driverregistry;

import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.driverregistry.DriverRegistry;
import com.senla.util.dao.GenericDao;

import java.util.List;
import java.util.Optional;

public interface IDriverRegistryDao extends GenericDao<DriverRegistry, Long>{
    List<DriverRegistry> getAll();
    List<DriverRegistry> getByDriverId(Long driverId);
    Optional<DriverRegistry> getEntry(Long driverId, Long companyId);
    List<DriverRegistry> findAllWithPagination(PaginationDetails paginationDetails);
}
