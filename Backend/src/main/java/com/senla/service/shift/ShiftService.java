package com.senla.service.shift;

import com.senla.dto.shift.ShiftStartDto;
import com.senla.model.shift.Shift;
import com.senla.util.service.GenericService;

import java.util.List;

public interface ShiftService extends GenericService<Shift, Long> {

    Shift startShift(ShiftStartDto shiftStartDto);
    Shift finishShift();

    List<Shift> getAllShiftsFullResponse();
    List<Shift> getDriverShiftsFullResponse();

    Shift getActiveShift();
}
