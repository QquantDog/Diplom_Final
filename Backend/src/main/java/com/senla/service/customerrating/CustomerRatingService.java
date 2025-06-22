package com.senla.service.customerrating;

import com.senla.dto.customerrating.CustomerRatingCreateDto;
import com.senla.dto.customerrating.CustomerRatingUpdateDto;
import com.senla.dto.pagination.PaginationRequest;
import com.senla.model.customerrating.CustomerRating;
import com.senla.util.service.GenericService;

import java.util.List;

public interface CustomerRatingService extends GenericService<CustomerRating, Long> {
    CustomerRating createCustomerRate(CustomerRatingCreateDto customerRatingCreateDto);
    CustomerRating updateCustomerRate(CustomerRatingUpdateDto customerRatingUpdateDto);

    List<CustomerRating> findAllRatingsWithRides(PaginationRequest paginationRequest);
    List<CustomerRating> findMyRatingsWithRides(PaginationRequest paginationRequest);

    CustomerRating findRatingByIdWithRides(Long ratingId);
    CustomerRating findMyRatingByIdWithRides(Long ratingId);

    void deleteMyRatingById(Long ratingId);
}
