package com.senla.service.driver;

import com.senla.dto.driver.UpdatePositionDto;
import com.senla.model.driver.Driver;
import com.senla.util.service.GenericService;


public interface DriverService extends GenericService<Driver, Long> {
    Driver updatePosition(UpdatePositionDto updatePositionDto);
    Driver findOwnDetails();
}
