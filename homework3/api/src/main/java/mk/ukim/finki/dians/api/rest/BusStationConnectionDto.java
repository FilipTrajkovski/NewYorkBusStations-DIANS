package mk.ukim.finki.dians.api.rest;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BusStationConnectionDto {

    private final BusStationDto busStation;
    private final List<BusDto> buses;

}
