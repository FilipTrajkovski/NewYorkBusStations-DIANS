package mk.ukim.finki.dians.api.rest;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchResponse {

    private final List<BusDto> buses;
    private final List<BusStationDto> busStations;

}
