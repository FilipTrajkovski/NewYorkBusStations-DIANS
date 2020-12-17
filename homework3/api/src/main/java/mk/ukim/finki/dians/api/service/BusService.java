package mk.ukim.finki.dians.api.service;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.dians.api.helper.BusMapper;
import mk.ukim.finki.dians.api.model.Bus;
import mk.ukim.finki.dians.api.repository.BusRepository;
import mk.ukim.finki.dians.api.rest.BusDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusService {

    private final BusRepository busRepository;

    public List<BusDto> getAllBuses() {
        List<Bus> buses = busRepository.findAll();

        return BusMapper.toDtoList(buses);
    }

    public Bus getBusById(Long id) {
        return busRepository.getOne(id);
    }

    public List<Bus> getBusesByIdIn(List<Long> ids) {
        return busRepository.getAllByIdIn(ids);
    }

}
