package com.senla.repository.cab;

import com.senla.model.cab.Cab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface CabRepository extends JpaRepository<Cab, Long>, CabRepositoryCustom {

    @Query("SELECT c FROM Cab c join fetch c.vehicle v join fetch v.brand join fetch c.company")
    List<Cab> getAllCabs();

    @Query("""
              select cab from Cab cab
              join cab.company c
              join c.registrationEntries re
              join re.driver d
              where d.driverId = :driverId""")
    List<Cab> getRegisteredCabs(Long driverId);

    @Query("""
              select cab from Cab cab
              join fetch cab.company c
              join fetch c.registrationEntries re
              join fetch re.driver d
              join fetch cab.vehicle v
              join fetch v.brand
              where d.driverId = :driverId
                  and cab.isOnShift = false""")
    List<Cab> getPossibleCabs(Long driverId);
}
