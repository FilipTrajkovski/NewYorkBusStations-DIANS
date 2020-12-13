package mk.ukim.finki.dians.api.rest;

import lombok.Data;

@Data
public class BusStationDto {

    private Long id;

    private String name;

    private String longitude;
    private String latitude;

}
