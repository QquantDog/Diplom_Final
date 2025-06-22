package com.senla.dao.vehiclebrand;

import com.senla.model.vehiclebrand.VehicleBrand;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class VehicleBrandDao extends AbstractLongDao<VehicleBrand> implements IVehicleBrandDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = VehicleBrand.class;
    }
}
