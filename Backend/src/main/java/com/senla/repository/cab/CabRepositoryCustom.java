package com.senla.repository.cab;

import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.cab.Cab;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CabRepositoryCustom {
    List<Cab> findWithFilterAndPagination(Specification<Cab> specification, PaginationDetails paginationDetails);
}
