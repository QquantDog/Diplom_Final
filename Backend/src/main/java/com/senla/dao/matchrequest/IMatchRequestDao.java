package com.senla.dao.matchrequest;

import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.matchrequest.MatchRequest;
import com.senla.util.dao.GenericDao;

import java.util.List;
import java.util.Optional;

public interface IMatchRequestDao extends GenericDao<MatchRequest, Long> {
    Optional<MatchRequest> matchRequestRideAndShift(Long rideId, Long shiftId);

    void evictOtherMatchRequestsForRide(Long rideId, Long shiftId);
    void evictAllMatchRequestsForRide(Long rideId);

    List<MatchRequest> findMatchRequests(PaginationDetails paginationDetails);
    Integer deactivateRequest(Long requestId);

    List<MatchRequest> matchRequestsToCheckForOfferStatus(Long requestId);
}
