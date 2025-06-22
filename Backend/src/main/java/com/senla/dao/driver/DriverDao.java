package com.senla.dao.driver;

import com.senla.model.driver.Driver;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DriverDao extends AbstractLongDao<Driver> implements IDriverDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = Driver.class;
    }

    @Override
    public Driver bindToUser(Long userId){

        return null;
    }
    @Override
    public Driver getDriverWithCompanies(Long driverId){
        TypedQuery<Driver> q = em.createQuery("""
            select d from Driver d
            left join fetch d.registrationEntries re
                where d.id = :driverId
            """, Driver.class);
        q.setParameter("driverId", driverId);
        return q.getResultList().getFirst();
    }

    @Override
    public Optional<Driver> findDriverCabWithinCompanies(Long driverId, Long cabId) {
        TypedQuery<Driver> q = em.createQuery("""
            select d from Driver d
            join d.registrationEntries re
            join re.taxiCompany t
            join t.cabs c
            where d.id = :driverId
                and c.id = :cabId
            """, Driver.class);
        q.setParameter("driverId", driverId);
        q.setParameter("cabId", cabId);
        return q.getResultStream().findFirst();
    }
}
