package mk.ukim.finki.dians.api.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.dians.api.rest.BusConnectionDto;
import mk.ukim.finki.dians.api.rest.BusStationConnectionDto;
import mk.ukim.finki.dians.api.service.BusNetworkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bus-networks")
public class BusNetworkController {

    private final BusNetworkService busNetworkService;

    @GetMapping(path = "/connections/bus/{busId}")
    public BusConnectionDto getConnectionsForBus(@PathVariable Long busId) {
        return busNetworkService.getAllConnectionsByBusId(busId);
    }

    @GetMapping(path = "/connections/bus-station/{busStationId}")
    public BusStationConnectionDto getConnectionsForBusStation(@PathVariable Long busStationId) {
        return busNetworkService.getAllConnectionsByBusStationId(busStationId);
    }

}
