package mk.ukim.finki.dians.api.repository;

import mk.ukim.finki.dians.api.model.Bus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

    List<Bus> getAllByIdIn(List<Long> ids);

    @Query(value = "SELECT * FROM bus AS b WHERE LOWER(b.name) LIKE :name LIMIT :limit", nativeQuery = true)
    List<Bus> getAllByNameLikeAndLimit(@Param("name") String name, @Param("limit") Integer limit);

}
