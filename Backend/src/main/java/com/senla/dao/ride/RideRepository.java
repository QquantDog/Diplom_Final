package com.senla.dao.ride;

import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.ride.Ride;
import com.senla.util.dao.GenericDao;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface RideRepository extends GenericDao<Ride, Long> {
    List<Ride> findActiveRide(Long customerId);
    Double getMinimalCartesianDistance(Point startPoint, Point endPoint);
    Optional<Ride> matchRideAndShift(Long rideId, Long driverId);

    Optional<Ride> verifyRideByCustomer(Long rideId, Long customerId);

    Optional<Ride> getRideWithRating(Long rideId);
    Optional<Ride> getRideForCustomer(Long rideId, Long customerId);

    Optional<Ride> getActiveRideForDriver(Long driverId);

    List<Ride> getAllRidesFullWithPaginationAndFiltering(Specification<Ride> specification, PaginationDetails paginationDetails);
    List<Ride> getCustomerRidesFullWithPaginationAndFiltering(Long customerId, Specification<Ride> specification, PaginationDetails paginationDetails);
    List<Ride> getDriverRidesFullWithPaginationAndFiltering(Long driverId, Specification<Ride> specification, PaginationDetails paginationDetails);

    Optional<Ride> findRideByRequest(Long requestId);

    Long countCustomerSpecification(Specification<Ride> specification, Long customerId);
    Long countDriverSpecification(Specification<Ride> specification, Long driverId);
}
