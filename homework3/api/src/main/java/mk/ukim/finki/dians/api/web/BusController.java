package mk.ukim.finki.dians.api.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.dians.api.rest.BusDto;
import mk.ukim.finki.dians.api.service.BusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/buses")
public class BusController {

    private final BusService busService;

    @GetMapping
    public List<BusDto> getAllBuses() {
        return busService.getAllBuses();
    }

}
