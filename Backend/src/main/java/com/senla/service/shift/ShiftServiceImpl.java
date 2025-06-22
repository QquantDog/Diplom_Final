package com.senla.service.shift;

import com.senla.dao.cab.CabDao;
import com.senla.dao.driver.DriverDao;
import com.senla.dao.rate.RateDao;
import com.senla.dao.shift.ShiftDao;
import com.senla.dto.shift.ShiftStartDto;
import com.senla.exception.DaoException;
import com.senla.model.cab.Cab;
import com.senla.model.driver.Driver;
import com.senla.model.rate.Rate;
import com.senla.model.shift.Shift;
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
public class ShiftServiceImpl extends AbstractLongIdGenericService<Shift> implements ShiftService {

    @Autowired
    private ShiftDao shiftDao;
    @Autowired
    private DriverDao driverDao;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RateDao rateDao;

    @Autowired
    private CabDao cabDao;

    @PostConstruct
    @Override
    public void init() {
        super.abstractDao = shiftDao;
    }



    @Override
    public List<Shift> getAllShiftsFullResponse(){
        return shiftDao.getAllShifts();
    }

    @Override
    public List<Shift> getDriverShiftsFullResponse() {
        return shiftDao.getDriverShifts(SecUtils.extractId());
    }

    @Override
    @Transactional
    public Shift startShift(ShiftStartDto shiftStartDto) {

        Long cabId = shiftStartDto.getCabId();
        Long driverId = SecUtils.extractId();
        Long cityId = shiftStartDto.getCityId();

        Cab cab = cabDao.findById(shiftStartDto.getCabId())
                .orElseThrow(()->new DaoException("Cab not found"));
        Rate rate = rateDao.getAutomaticRateForDriver(cabId, cityId)
                .orElseThrow(()->new DaoException("Automatic rate match has 0 results"));
        Driver driver = driverDao.findDriverCabWithinCompanies(driverId, cabId)
                .orElseThrow(()->new DaoException("No relevant driver register entry in taxi company for cab is found"));

        if(driver.getIsOnShift()) throw new DaoException("Couldn't start shift - driver is already on shift");
        if(cab.getIsOnShift()) throw new DaoException("Couldn't start shift - cab is already in use");

        Shift shift = modelMapper.map(shiftStartDto, Shift.class);
        shift.setStarttime(LocalDateTime.now());
        shift.setEndtime(null);

        shift.setCab(cab);
        shift.setDriver(driver);
        shift.setRate(rate);

        cab.setIsOnShift(true);
        driver.setIsOnShift(true);
        driver.setCurrentPoint(shiftStartDto.getShiftStartPoint());

        return abstractDao.create(shift);
    }

    @Override
    public Shift getActiveShift() {
        return shiftDao.getActiveShift(SecUtils.extractId())
                .orElseThrow(()->new DaoException("Shift not found"));
    }

    @Override
    @Transactional
    public Shift finishShift() {
        Long driverId = SecUtils.extractId();
        List<Shift> shifts = shiftDao.getDriverActiveShifts(driverId);
        if(shifts.isEmpty()) throw new DaoException("No relevant driver active shifts found");
        if(shifts.size() > 1) throw new IllegalStateException("Found more than 1 active shifts - internal error");

        Shift shift = shifts.getFirst();
        if(!shift.getDriver().getDriverId().equals(driverId)) throw new DaoException("Driver does not match for shift");
        if(shift.getEndtime() != null) throw new DaoException("Shift is already finished");

        Cab cab = shift.getCab();
        Driver driver = shift.getDriver();

        if(!cab.getIsOnShift()) throw new DaoException("Couldn't finish shift - shift is already finished");
        if(!driver.getIsOnShift()) throw new DaoException("Driver is not active to finish shift");

        cab.setIsOnShift(false);
        driver.finishShiftAndRestore();
        shift.setEndtime(LocalDateTime.now());

//        очистить лист матчей?
        return shiftDao.update(shift);
    }

}
