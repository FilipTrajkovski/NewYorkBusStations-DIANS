package mk.ukim.finki.dians.api.model;

import lombok.Data;
import mk.ukim.finki.dians.api.model.id.BusNetworkId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@Entity
@IdClass(BusNetworkId.class)
@Table(name = "bus_network")
public class BusNetwork {

    @Id
    private Long busId;

    @Id
    private Long busStationId;

}
