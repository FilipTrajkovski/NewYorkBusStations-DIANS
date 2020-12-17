package mk.ukim.finki.dians.api.repository;

import mk.ukim.finki.dians.api.model.BusStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusStationRepository extends JpaRepository<BusStation, Long> {

    List<BusStation> getAllByIdIn(List<Long> ids);

}
