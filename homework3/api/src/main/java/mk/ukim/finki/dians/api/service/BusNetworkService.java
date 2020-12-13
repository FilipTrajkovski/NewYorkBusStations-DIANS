package mk.ukim.finki.dians.api.service;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.dians.api.helper.BusMapper;
import mk.ukim.finki.dians.api.helper.BusStationMapper;
import mk.ukim.finki.dians.api.model.Bus;
import mk.ukim.finki.dians.api.model.BusNetwork;
import mk.ukim.finki.dians.api.model.BusStation;
import mk.ukim.finki.dians.api.repository.BusNetworkRepository;
import mk.ukim.finki.dians.api.rest.*;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BusNetworkService {

    private final BusNetworkRepository busNetworkRepository;

    private final BusService busService;
    private final BusStationService busStationService;

    public BusConnectionDto getAllConnectionsByBusId(Long busId) {

        List<BusNetwork> foundBusNetworks = busNetworkRepository.getAllByBusId(busId);

        Bus bus = busService.getBusById(busId);

        List<Long> busStationIds = foundBusNetworks
                .stream()
                .map(BusNetwork::getBusStationId)
                .collect(toList());

        List<BusStation> busStations = busStationService.getBusStationsByIdIn(busStationIds);

        BusDto busDto = BusMapper.toDto(bus);
        List<BusStationDto> busStationDtos = BusStationMapper.toDtoList(busStations);

        return BusConnectionDto
                .builder()
                .bus(busDto)
                .busStations(busStationDtos)
                .build();
    }


    public BusStationConnectionDto getAllConnectionsByBusStationId(Long busStationId) {

        List<BusNetwork> foundBusNetworks = busNetworkRepository.getAllByBusStationId(busStationId);

        BusStation busStation = busStationService.getBusStationById(busStationId);

        List<Long> busIds = foundBusNetworks
                .stream()
                .map(BusNetwork::getBusId)
                .collect(toList());

        List<Bus> buses = busService.getBusesByIdIn(busIds);

        BusStationDto busStationDto = BusStationMapper.toDto(busStation);
        List<BusDto> busDtos = BusMapper.toDtoList(buses);

        return BusStationConnectionDto
                .builder()
                .busStation(busStationDto)
                .buses(busDtos)
                .build();
    }

}
