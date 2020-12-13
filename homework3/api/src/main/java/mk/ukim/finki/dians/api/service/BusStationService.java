package mk.ukim.finki.dians.api.service;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.dians.api.helper.BusStationMapper;
import mk.ukim.finki.dians.api.model.BusStation;
import mk.ukim.finki.dians.api.repository.BusStationRepository;
import mk.ukim.finki.dians.api.rest.BusStationDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusStationService {

    private final BusStationRepository busStationRepository;

    public List<BusStationDto> getAllBusStations() {
        List<BusStation> busStations = busStationRepository.findAll();

        return BusStationMapper.toDtoList(busStations);
    }

    public BusStation getBusStationById(Long id) {
        return busStationRepository.getOne(id);
    }

    public List<BusStation> getBusStationsByIdIn(List<Long> ids) {
        return busStationRepository.getAllByIdIn(ids);
    }

}
