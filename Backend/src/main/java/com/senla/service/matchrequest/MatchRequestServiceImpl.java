package com.senla.service.matchrequest;

import com.senla.dao.matchrequest.MatchRequestDao;
import com.senla.dao.ride.RideRepositoryImpl;
import com.senla.dto.pagination.PaginationRequest;
import com.senla.exception.DaoException;
import com.senla.exception.PaginationException;
import com.senla.model.matchrequest.MatchRequest;
import com.senla.model.ride.Ride;
import com.senla.types.MatchRequestStatus;
import com.senla.types.RideStatus;
import com.senla.util.pagination.PaginationUtils;
import com.senla.util.service.AbstractLongIdGenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MatchRequestServiceImpl extends AbstractLongIdGenericService<MatchRequest> implements MatchRequestService {

    private static final Logger logger = LoggerFactory.getLogger(MatchRequestServiceImpl.class);

    @Autowired
    private MatchRequestDao matchRequestDao;
    @Autowired
    private RideRepositoryImpl rideDao;

    @Override
    protected void init() {
        super.abstractDao = matchRequestDao;
    }

    @Override
    public List<MatchRequest> findAllMatches(PaginationRequest paginationRequest) {
        return matchRequestDao.findMatchRequests(PaginationUtils
                .getOffsetByCountAndParams((int) matchRequestDao.count(), paginationRequest)
                    .orElseThrow(PaginationException::new));
    }

    @Override
    @Transactional
    public void deactivateRequest(Long requestId) {
        if(matchRequestDao.deactivateRequest(requestId) == 0) throw new DaoException("No suitable match request");
        List<MatchRequest> requests = matchRequestDao.matchRequestsToCheckForOfferStatus(requestId);
        if(requests.isEmpty()) {
            Ride ride = rideDao.findRideByRequest(requestId)
                    .orElseThrow(()->new DaoException("No ride found"));
            if(ride.getStatus() != RideStatus.PENDING) throw new DaoException("Inconsistent ride status");
            ride.setRideEndTime(LocalDateTime.now());
            ride.setStatus(RideStatus.CANCELLED);
        }
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 */1 * * * *")
    public void deactivateAllNotActiveRequestsForTimeout() {
//        logger.info("SCHEDULER started");
//        List<Ride> ridesForDeactivation = rideDao.findRidesForDeactivation();
//        if(ridesForDeactivation.isEmpty()) {
//            logger.info("SCHEDULER no rides found for deactivation");
//        } else{
//            logger.info("SCHEDULER found {} ride(s) for deactivation", ridesForDeactivation.size());
//            ridesForDeactivation.forEach(ride -> System.out.println(ride.getId()));
//            ridesForDeactivation.forEach(ride -> {
//                ride.setRideEndTime(LocalDateTime.now());
//                ride.setStatus(RideStatus.CANCELLED);
//                ride.getMatchRequests().stream().filter(matchRequest ->
//                    matchRequest.getMatchRequestStatus() == MatchRequestStatus.OFFERED
//                ).forEach(m ->
//                    m.setMatchRequestStatus(MatchRequestStatus.STALE)
//                );
//            });
//
//        }

    }

}
