package eu.brokoko.thymely.repository;

import eu.brokoko.thymely.domain.Workperiod;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Workperiod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkperiodRepository extends JpaRepository<Workperiod, Long> {

}
