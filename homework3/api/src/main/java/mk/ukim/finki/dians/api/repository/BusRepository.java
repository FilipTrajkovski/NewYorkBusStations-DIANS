package mk.ukim.finki.dians.api.repository;

import mk.ukim.finki.dians.api.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
}
