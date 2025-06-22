package com.senla.dao.vehicle;

import com.senla.model.vehicle.Vehicle;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class VehicleDao extends AbstractLongDao<Vehicle> implements IVehicleDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = Vehicle.class;
    }

    @Override
    public List<Vehicle> findWithBrands() {
        TypedQuery<Vehicle> q = em.createQuery("""
                SELECT v FROM Vehicle v
                join fetch v.brand
                """, Vehicle.class);
        return q.getResultList();
    }

    @Override
    public Optional<Vehicle> findWithBrandById(Long vehicleId) {
        TypedQuery<Vehicle> q = em.createQuery("""
                SELECT v FROM Vehicle v
                join fetch v.brand b
                where v.id = :vehicleId
                """, Vehicle.class);
        q.setParameter("vehicleId", vehicleId);
        return q.getResultStream().findFirst();
    }

}
