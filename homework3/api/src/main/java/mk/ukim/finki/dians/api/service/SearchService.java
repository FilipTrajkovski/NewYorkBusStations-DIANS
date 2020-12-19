package mk.ukim.finki.dians.api.service;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.dians.api.helper.BusMapper;
import mk.ukim.finki.dians.api.helper.BusStationMapper;
import mk.ukim.finki.dians.api.model.Bus;
import mk.ukim.finki.dians.api.model.BusStation;
import mk.ukim.finki.dians.api.repository.BusRepository;
import mk.ukim.finki.dians.api.repository.BusStationRepository;
import mk.ukim.finki.dians.api.rest.SearchResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private static final Integer LIMIT = 20;

    private final BusRepository busRepository;
    private final BusStationRepository busStationRepository;

    public SearchResponse searchBusData(String query) {

        String queryWithWildcards = "%" + query.toLowerCase()  + "%";
        List<Bus> buses = busRepository.getAllByNameLikeAndLimit(queryWithWildcards, LIMIT);
        List<BusStation> busStations = busStationRepository.getAllByNameLikeAndLimit(queryWithWildcards, LIMIT);

        return SearchResponse
                .builder()
                .buses(BusMapper.toDtoList(buses))
                .busStations(BusStationMapper.toDtoList(busStations))
                .build();
    }

}
