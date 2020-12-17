package mk.ukim.finki.dians.api.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.dians.api.rest.BusStationDto;
import mk.ukim.finki.dians.api.service.BusStationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/bus-stations")
public class BusStationController {

    private final BusStationService busStationService;

    @GetMapping
    public List<BusStationDto> getAllBusStations() {
        return busStationService.getAllBusStations();
    }

}
