package mk.ukim.finki.dians.api.repository;

import mk.ukim.finki.dians.api.model.BusStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStationRepository extends JpaRepository<BusStation, Long> {
}
