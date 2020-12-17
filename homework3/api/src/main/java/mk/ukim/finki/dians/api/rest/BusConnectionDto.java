package mk.ukim.finki.dians.api.rest;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BusConnectionDto {

    private final BusDto bus;
    private final List<BusStationDto> busStations;

}
