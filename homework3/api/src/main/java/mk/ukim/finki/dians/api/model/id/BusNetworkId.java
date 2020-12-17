package mk.ukim.finki.dians.api.model.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusNetworkId implements Serializable {

    private Long busId;

    private Long busStationId;

}
