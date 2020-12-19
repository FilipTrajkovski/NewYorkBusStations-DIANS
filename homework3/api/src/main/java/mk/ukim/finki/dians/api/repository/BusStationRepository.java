package mk.ukim.finki.dians.api.repository;

import mk.ukim.finki.dians.api.model.BusStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusStationRepository extends JpaRepository<BusStation, Long> {

    List<BusStation> getAllByIdIn(List<Long> ids);

    @Query(value = "SELECT * FROM bus_station AS bs WHERE LOWER(bs.name) LIKE :name LIMIT :limit", nativeQuery = true)
    List<BusStation> getAllByNameLikeAndLimit(@Param("name") String name, @Param("limit") Integer limit);

}
