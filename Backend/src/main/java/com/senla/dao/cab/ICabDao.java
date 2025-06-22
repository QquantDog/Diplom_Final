package com.senla.dao.cab;

import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.cab.Cab;
import com.senla.util.dao.GenericDao;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ICabDao extends GenericDao<Cab, Long> {
    List<Cab> getAllCabs();
    List<Cab> getRegisteredCabs(Long driverId);
    List<Cab> getPossibleCabs(Long driverId);
    List<Cab> findWithFilterAndPagination(Specification<Cab> specification, PaginationDetails paginationDetails);
}
