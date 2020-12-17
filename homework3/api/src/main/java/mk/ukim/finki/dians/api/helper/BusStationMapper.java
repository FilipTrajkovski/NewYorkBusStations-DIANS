package mk.ukim.finki.dians.api.helper;

import mk.ukim.finki.dians.api.model.BusStation;
import mk.ukim.finki.dians.api.rest.BusStationDto;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class BusStationMapper {

    private BusStationMapper() {}

    public static BusStationDto toDto(BusStation busStation) {

        BusStationDto busStationDto = new BusStationDto();

        busStationDto.setId(busStation.getId());
        busStationDto.setName(busStation.getName());
        busStationDto.setLongitude(busStation.getLongitude());
        busStationDto.setLatitude(busStation.getLatitude());

        return busStationDto;
    }

    public static List<BusStationDto> toDtoList(List<BusStation> busStations) {
        return busStations
                .stream()
                .map(BusStationMapper::toDto)
                .collect(toList());
    }

}
