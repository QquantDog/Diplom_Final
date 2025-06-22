package com.senla.service.customerrating;

import com.senla.dao.customerrating.CustomerRatingDao;
import com.senla.dao.ride.RideRepository;
import com.senla.dto.customerrating.CustomerRatingCreateDto;
import com.senla.dto.customerrating.CustomerRatingUpdateDto;
import com.senla.dto.pagination.PaginationRequest;
import com.senla.exception.DaoException;
import com.senla.exception.PaginationException;
import com.senla.model.customerrating.CustomerRating;
import com.senla.model.ride.Ride;
import com.senla.util.pagination.PaginationUtils;
import com.senla.util.sec.SecUtils;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CustomerRatingServiceImpl extends AbstractLongIdGenericService<CustomerRating> implements CustomerRatingService {

    @Autowired
    private CustomerRatingDao customerRatingDao;
    @Autowired
    private RideRepository rideDao;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @PostConstruct
    protected void init() {
        super.abstractDao = customerRatingDao;
    }


    @Override
    @Transactional
    public CustomerRating createCustomerRate(CustomerRatingCreateDto customerRatingCreateDto) {
        Long rideId = customerRatingCreateDto.getRideId();
        Long customerId = SecUtils.extractId();

        Ride ride = rideDao.getRideForCustomer(rideId, customerId)
                .orElseThrow(()->new DaoException("Ride not found"));

        CustomerRating customerRating = modelMapper.map(customerRatingCreateDto, CustomerRating.class);
        customerRating.setRide(ride);
        ride.setCustomerRating(customerRating);
        customerRating.setCreatedAt(LocalDateTime.now());

        return abstractDao.create(customerRating);
    }

    @Override
    @Transactional
    public CustomerRating updateCustomerRate(CustomerRatingUpdateDto customerRatingUpdateDto) {
        Long rideId = customerRatingUpdateDto.getRideId();
        Long customerId = SecUtils.extractId();

        Ride ride = rideDao.getRideWithRating(rideId)
                .orElseThrow(()->new DaoException("Rating for ride not found"));
        if(!ride.getCustomer().getId().equals(customerId)) throw new DaoException("Ride has different customer");

        CustomerRating cr = ride.getCustomerRating();
        modelMapper.map(customerRatingUpdateDto, cr);
        cr.setUpdatedAt(LocalDateTime.now());

        return abstractDao.update(cr);
    }

    @Override
    public List<CustomerRating> findAllRatingsWithRides(PaginationRequest paginationRequest) {
        return customerRatingDao.findAllRatingsWithRides(PaginationUtils
                .getOffsetByCountAndParams((int) customerRatingDao.count(), paginationRequest)
                    .orElseThrow(PaginationException::new));
    }

    @Override
    public List<CustomerRating> findMyRatingsWithRides(PaginationRequest paginationRequest) {
        return customerRatingDao.findCustomerRatingsWithRides(SecUtils.extractId(), PaginationUtils
                .getOffsetByCountAndParams((int) customerRatingDao.count(), paginationRequest)
                    .orElseThrow(PaginationException::new));
    }

    @Override
    @Transactional
    public CustomerRating findRatingByIdWithRides(Long ratingId) {
        return customerRatingDao.findRatingByIdWithRides(ratingId)
                .orElseThrow(()->new DaoException("Rating not found"));
    }

    @Override
    @Transactional
    public CustomerRating findMyRatingByIdWithRides(Long ratingId) {
        return customerRatingDao.findCustomerRatingByIdWithRides(ratingId, SecUtils.extractId())
                .orElseThrow(()->new DaoException("Rating not found"));
    }

    @Override
    @Transactional
    public void deleteMyRatingById(Long ratingId) {
        customerRatingDao.deleteById(customerRatingDao.findCustomerRatingByIdWithRides(ratingId, SecUtils.extractId())
                .orElseThrow(()->new DaoException("Rating Not found")).getId());
    }
}
