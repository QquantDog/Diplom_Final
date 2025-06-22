package com.senla.dao.rate;

import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.rate.Rate;
import com.senla.util.dao.GenericDao;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface IRateDao extends GenericDao<Rate, Long> {
    List<Rate> getRatesFull();
    Optional<Rate> getRateById(Long rateId);
    Optional<Rate> getAutomaticRateForDriver(Long cabId, Long cityId);
    List<Rate> getAllWithFilterAndPagination(Specification<Rate> specification, PaginationDetails paginationDetails);
}
