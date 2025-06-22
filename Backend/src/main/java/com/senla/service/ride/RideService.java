package com.senla.service.ride;

import com.senla.dto.pagination.PaginationRequest;
import com.senla.dto.ride.*;
import com.senla.model.ride.Ride;
import com.senla.util.page.Page;
import com.senla.util.service.GenericService;

import java.util.List;

public interface RideService extends GenericService<Ride, Long> {

    Ride findRideById(Long id);
    List<Ride> getAllRidesFullWithPaginationAndFiltering(RideFilterDto filterDto, PaginationRequest paginationRequest);
    Page<List<RideCustomerResponseDto>> getMyCustomerRides(RideFilterDto filterDto, PaginationRequest paginationRequest);
    Page<List<RideDriverResponseDto>> getMyDriverRides(RideFilterDto filterDto, PaginationRequest paginationRequest);

    Ride initializeRide(RideCreateDto rideCreateDto);
    Ride matchConfirmRide(RideMatchDto rideMatchDto);
    void matchDeclineRide(RideMatchDto rideMatchDto);
    Ride waitForClient();
    Ride startRide();
    Ride endRide(RideEndDto rideEndDto);
    Ride cancelRide(RideCancelDto rideCancelDto);

    Ride activatePromocodeOnRide(RidePromocodeEnterDto ridePromocodeEnterDto);

    Ride rideTip(RideTipDto rideTipDto);
}
