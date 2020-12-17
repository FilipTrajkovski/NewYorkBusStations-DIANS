package mk.ukim.finki.dians.api.repository;

import mk.ukim.finki.dians.api.model.BusNetwork;
import mk.ukim.finki.dians.api.model.id.BusNetworkId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusNetworkRepository extends JpaRepository<BusNetwork, BusNetworkId> {

    List<BusNetwork> getAllByBusId(Long busId);
    List<BusNetwork> getAllByBusStationId(Long busStationId);

}
