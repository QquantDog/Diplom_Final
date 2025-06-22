package com.senla.dao.customerrating;

import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.customerrating.CustomerRating;
import com.senla.util.dao.GenericDao;

import java.util.List;
import java.util.Optional;

public interface ICustomerRatingDao extends GenericDao<CustomerRating, Long> {

    List<CustomerRating> findAllRatingsWithRides(PaginationDetails paginationDetails);
    List<CustomerRating> findCustomerRatingsWithRides(Long customerId, PaginationDetails paginationDetails);

    Optional<CustomerRating> findRatingByIdWithRides(Long ratingId);
    Optional<CustomerRating> findCustomerRatingByIdWithRides(Long ratingId, Long customerId);
}
