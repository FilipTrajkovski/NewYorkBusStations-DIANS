package mk.ukim.finki.dians.api.helper;

import mk.ukim.finki.dians.api.model.Bus;
import mk.ukim.finki.dians.api.rest.BusDto;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class BusMapper {

    private BusMapper() {}

    public static BusDto toDto(Bus bus) {
        BusDto busDto = new BusDto();

        busDto.setId(bus.getId());
        busDto.setName(bus.getName());

        return busDto;
    }

    public static List<BusDto> toDtoList(List<Bus> buses) {
        return buses
                .stream()
                .map(BusMapper::toDto)
                .collect(toList());
    }

}
