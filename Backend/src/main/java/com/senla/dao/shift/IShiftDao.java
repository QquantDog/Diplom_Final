package com.senla.dao.shift;

import com.senla.model.shift.Shift;
import com.senla.util.dao.GenericDao;
import jakarta.persistence.Tuple;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface IShiftDao extends GenericDao<Shift, Long> {
    List<Shift> getAllShifts();
    List<Shift> getDriverShifts(Long driverId);
    List<Shift> getDriverActiveShifts(Long driverId);
    List<Tuple> getMatchingShifts(Long rateId, Point customerStartPoint, Double radiusThresholdMeters);
    Optional<Shift> getActiveShift(Long driverId);
}
