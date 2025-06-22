package com.senla.service.ride;

import com.senla.dao.driver.DriverDao;
import com.senla.dao.matchrequest.MatchRequestDao;
import com.senla.dao.payment.PaymentDao;
import com.senla.dao.promocode.PromocodeDao;
import com.senla.dao.rate.RateDao;
import com.senla.dao.ride.RideRepositoryImpl;
import com.senla.dao.shift.ShiftDao;
import com.senla.dao.user.UserDao;
import com.senla.dto.pagination.PaginationRequest;
import com.senla.dto.ride.*;
import com.senla.exception.DaoException;
import com.senla.exception.PaginationException;
import com.senla.model.driver.Driver;
import com.senla.model.matchrequest.MatchRequest;
import com.senla.model.payment.Payment;
import com.senla.model.promocode.Promocode;
import com.senla.model.rate.Rate;
import com.senla.model.ride.Ride;
import com.senla.model.shift.Shift;
import com.senla.model.user.User;
import com.senla.specification.RideSpecification;
import com.senla.types.MatchRequestStatus;
import com.senla.types.RideStatus;
import com.senla.util.page.Page;
import com.senla.util.pagination.PaginationUtils;
import com.senla.util.sec.SecUtils;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Tuple;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RideServiceImpl extends AbstractLongIdGenericService<Ride> implements RideService {

    @Autowired
    private RideRepositoryImpl rideDao;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RateDao rateDao;
    @Autowired
    private ShiftDao shiftDao;
    @Autowired
    private MatchRequestDao matchRequestDao;
    @Autowired
    private PromocodeDao promocodeDao;
    @Autowired
    private DriverDao driverDao;
    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private PriceCalculation priceCalculation;

    @PostConstruct
    @Override
    public void init() {
        super.abstractDao = rideDao;
    }


    @Override
    public Ride findRideById(Long id) {
        return rideDao.findById(id).orElseThrow(()->new DaoException("Ride not found"));
    }

    @Override
    @Transactional
    public List<Ride> getAllRidesFullWithPaginationAndFiltering(RideFilterDto filterDto, PaginationRequest paginationRequest) {
        return rideDao.getAllRidesFullWithPaginationAndFiltering(RideSpecification.buildSpecification(filterDto), PaginationUtils
                .getOffsetByCountAndParams((int) rideDao.count(), paginationRequest)
                    .orElseThrow(PaginationException::new));
    }

    @Override
    @Transactional
    public Page<List<RideCustomerResponseDto>> getMyCustomerRides(RideFilterDto filterDto, PaginationRequest paginationRequest) {
        var specification = RideSpecification.buildSpecification(filterDto);
        var count = rideDao.countCustomerSpecification(specification, SecUtils.extractId());
        var rides = rideDao.getCustomerRidesFullWithPaginationAndFiltering(SecUtils.extractId(), specification, PaginationUtils
                .getOffsetByCountAndParams(count.intValue(), paginationRequest)
                    .orElseThrow(PaginationException::new));
        return Page.<List<RideCustomerResponseDto>>builder().data(rides.stream().map((ride) -> modelMapper.map(ride, RideCustomerResponseDto.class)).toList()).total(count.intValue()).page(paginationRequest.getPage()).limit(paginationRequest.getLimit()).build();
    }

    @Override
    @Transactional
    public Page<List<RideDriverResponseDto>> getMyDriverRides(RideFilterDto filterDto, PaginationRequest paginationRequest) {
        var specification = RideSpecification.buildSpecification(filterDto);
        var count = rideDao.countDriverSpecification(specification, SecUtils.extractId());
        var rides = rideDao.getDriverRidesFullWithPaginationAndFiltering(SecUtils.extractId(), specification, PaginationUtils
                .getOffsetByCountAndParams(count.intValue(), paginationRequest)
                .orElseThrow(PaginationException::new));
        return Page.<List<RideDriverResponseDto>>builder().data(rides.stream().map((ride) -> modelMapper.map(ride, RideDriverResponseDto.class)).toList()).total(count.intValue()).page(paginationRequest.getPage()).limit(paginationRequest.getLimit()).build();
    }

    @Override
    @Transactional
    public Ride initializeRide(RideCreateDto rideCreateDto) {

        Long customerId = SecUtils.extractId();
        Long rateId = rideCreateDto.getRateId();
        String paymentMethod = rideCreateDto.getPaymentMethod();

        List<Ride> activeRides = rideDao.findActiveRide(customerId);
        if(activeRides.size() > 1) throw new RuntimeException("Inconsistent rides state - more than 1 active ride");
        if(activeRides.size() == 1) throw new DaoException("Could not create ride - placed ride already exists");

        Ride rideToStart = modelMapper.map(rideCreateDto, Ride.class);

        User customer = userDao.findCustomer(customerId);

        rideToStart.setCustomer(customer);
        rideToStart.setCreatedAt(LocalDateTime.now());

        Payment payment = Payment.builder().method(paymentMethod).ride(rideToStart).build();
        rideToStart.setPayment(payment);

        Rate rate = rateDao.findById(rateId)
                .orElseThrow(()->new DaoException("Could not find rate with id " + rateId));
        rideToStart.setRate(rate);

        BigDecimal expectedDistance = BigDecimal.valueOf(rideDao.getMinimalCartesianDistance(
                rideCreateDto.getStartPoint(), rideCreateDto.getEndPoint()
        ));
        rideToStart.setRideDistanceExpectedMeters(expectedDistance.setScale(0, RoundingMode.CEILING));
        rideToStart.setStatus(RideStatus.PENDING);

        List<Tuple> matchedShiftsWithDistance = shiftDao.getMatchingShifts(rateId, rideToStart.getStartPoint(), 20000.0);
        matchedShiftsWithDistance.forEach(entry -> {
            MatchRequest matchRequest = MatchRequest.builder()
                    .ride(rideToStart)
                    .shift(entry.get(0, Shift.class))
                    .createdAt(LocalDateTime.now())
                    .initDistance(BigDecimal.valueOf(entry.get(1, Double.class)).setScale(0, RoundingMode.CEILING))
                    .matchRequestStatus(MatchRequestStatus.OFFERED)
                    .build();
            matchRequestDao.create(matchRequest);
        });

        return rideDao.create(rideToStart);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Ride matchConfirmRide(RideMatchDto rideMatchDto){
        Shift activeShift = shiftDao.getActiveShift(SecUtils.extractId())
                .orElseThrow(()->new DaoException("Active shift not found"));
        Long rideId = rideMatchDto.getRideId();

        Ride matchedRide = rideDao.matchRideAndShift(rideId, activeShift.getShiftId())
                .orElseThrow(()->new DaoException("No match found"));

        if(matchedRide.getStatus() != RideStatus.PENDING) throw new DaoException("Ride is not at PENDING state: " + matchedRide.getStatus());

        matchedRide.setShift(activeShift);
        matchedRide.setRideAcceptedAt(LocalDateTime.now());
        matchedRide.setStatus(RideStatus.ACCEPTED);

        Driver driver = matchedRide.getShift().getDriver();
        driver.setIsOnRide(true);
        driverDao.update(driver);

        MatchRequest mr = matchRequestDao.matchRequestRideAndShift(rideId, activeShift.getShiftId())
                .orElseThrow(()->new DaoException("No match found"));
        mr.setMatchRequestStatus(MatchRequestStatus.ACCEPTED);
        matchRequestDao.update(mr);
        matchRequestDao.evictOtherMatchRequestsForRide(rideId, activeShift.getShiftId());

        return rideDao.update(matchedRide);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void matchDeclineRide(RideMatchDto rideMatchDto) {
        Shift activeShift = shiftDao.getActiveShift(SecUtils.extractId())
                .orElseThrow(()->new DaoException("Active shift not found"));
        Long rideId = rideMatchDto.getRideId();

        MatchRequest matchRequest = matchRequestDao.matchRequestRideAndShift(rideId, activeShift.getShiftId())
                .orElseThrow(()->new DaoException("No match found"));
        matchRequest.setMatchRequestStatus(MatchRequestStatus.DECLINED);

        List<MatchRequest> requests = matchRequestDao.matchRequestsToCheckForOfferStatus(matchRequest.getId());
        if(requests.isEmpty()) {
            Ride ride = rideDao.findRideByRequest(matchRequest.getId())
                    .orElseThrow(()->new DaoException("No ride found"));
            if(ride.getStatus() != RideStatus.PENDING) throw new DaoException("Inconsistent ride status");
            ride.setRideEndTime(LocalDateTime.now());
            ride.setStatus(RideStatus.CANCELLED);
            rideDao.update(ride);
        }
        matchRequestDao.update(matchRequest);
    }

    @Override
    @Transactional
    public Ride waitForClient(){
        Ride ride = rideDao.getActiveRideForDriver(SecUtils.extractId())
                .orElseThrow(()->new DaoException("No active ride for driver found"));

        if(ride.getStatus() != RideStatus.ACCEPTED) throw new DaoException("Ride is not at ACCEPTED state: " + ride.getStatus());
        ride.setStatus(RideStatus.WAITING_CLIENT);
        ride.setRideDriverWaiting(LocalDateTime.now());

        return rideDao.update(ride);
    }

    @Override
    @Transactional
    public Ride startRide(){
        Ride ride = rideDao.getActiveRideForDriver(SecUtils.extractId())
                .orElseThrow(()->new DaoException("No active ride for driver found"));

        if(ride.getStatus() != RideStatus.ACCEPTED &&
                ride.getStatus() != RideStatus.WAITING_CLIENT) throw new DaoException("Ride is not at ACCEPTED/WAITING_CLIENT state: " + ride.getStatus());

        LocalDateTime startTime = LocalDateTime.now();
        if(ride.getStatus() == RideStatus.ACCEPTED) ride.setRideDriverWaiting(startTime);
        ride.setRideStartTime(startTime);
        ride.setStatus(RideStatus.IN_WAY);

        return rideDao.update(ride);
    }

    @Override
    @Transactional
    public Ride endRide(RideEndDto rideEndDto) {
        Ride ride = rideDao.getActiveRideForDriver(SecUtils.extractId())
                .orElseThrow(()->new DaoException("No active ride for driver found"));

        if(ride.getStatus() != RideStatus.IN_WAY) throw new DaoException("Ride is not in IN_WAY state: " + ride.getStatus());
        ride.setStatus(RideStatus.COMPLETED);
        ride.setRideEndTime(LocalDateTime.now());

        Optional<Rate> rateResp = rateDao.findById(ride.getRate().getId());
        if(rateResp.isEmpty()) throw new DaoException("Could not find rate with id " + ride.getRate().getId());
        Rate rate = rateResp.get();

        if(rideEndDto.getActualDistance() == null) {
            ride.setRideDistanceActualMeters(ride.getRideDistanceExpectedMeters());
        }
        else {
            ride.setRideDistanceActualMeters(rideEndDto.getActualDistance().setScale(0, RoundingMode.CEILING));
        }
        ride.setRideActualPrice(priceCalculation.calculateActualPricePerTrip(ride, rate).setScale(1, RoundingMode.UP));

        Driver driver = ride.getShift().getDriver();
        driver.setIsOnRide(false);
        driverDao.update(driver);

        Payment payment = ride.getPayment();
        payment.setOverallPrice(priceCalculation.calculateFinishPrice(ride));
        paymentDao.update(payment);

        return rideDao.update(ride);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Ride cancelRide(RideCancelDto rideCancelDto) {
        Long customerId = SecUtils.extractId();
        Long rideId = rideCancelDto.getRideId();

        Ride ride = rideDao.verifyRideByCustomer(rideId, customerId)
                .orElseThrow(()->new DaoException("Ride for customer not found"));

        if(ride.getStatus() == RideStatus.CANCELLED || ride.getStatus() == RideStatus.COMPLETED) throw new DaoException("Ride is not active. Current status is " + ride.getStatus());
        LocalDateTime cancelTime = LocalDateTime.now();

        if(ride.getStatus() == RideStatus.PENDING){
            matchRequestDao.evictAllMatchRequestsForRide(rideId);
        }
        else if(ride.getStatus() == RideStatus.ACCEPTED || ride.getStatus() == RideStatus.WAITING_CLIENT || ride.getStatus() == RideStatus.IN_WAY){
            Driver driver = ride.getShift().getDriver();
            driver.setIsOnRide(false);
            driverDao.update(driver);
        }
        ride.setRideEndTime(cancelTime);
        ride.setStatus(RideStatus.CANCELLED);
        return rideDao.update(ride);
    }

    @Override
    @Transactional
    public Ride activatePromocodeOnRide(RidePromocodeEnterDto ridePromocodeEnterDto){
        Long customerId = SecUtils.extractId();
        Long rideId = ridePromocodeEnterDto.getRideId();

        Ride ride = rideDao.verifyRideByCustomer(rideId, customerId)
                .orElseThrow(()->new DaoException("Ride not found"));
        if(ride.getStatus() == RideStatus.COMPLETED || ride.getStatus() == RideStatus.CANCELLED) throw new DaoException("Cannot activate promocode - ride is either finished or aborted");

        String code = ridePromocodeEnterDto.getPromocodeEnterCode();
        Promocode promocode = promocodeDao.findByCode(code);
        ride.setPromocode(promocode);

        return rideDao.update(ride);
    }

    @Override
    @Transactional
    public Ride rideTip(RideTipDto rideTipDto) {
        Long customerId = SecUtils.extractId();
        Long rideId = rideTipDto.getRideId();

        Ride ride = rideDao.verifyRideByCustomer(rideId, customerId)
                .orElseThrow(()->new DaoException("Ride for customer not found"));
        switch (ride.getStatus()) {
            case CANCELLED:
            case COMPLETED:
            case PENDING: {
                throw new DaoException("Ride is not active to tip the driver - status:  " + ride.getStatus());
            }
        }
        ride.setRideTip(rideTipDto.getTipAmount());
        return rideDao.update(ride);
    }

}
