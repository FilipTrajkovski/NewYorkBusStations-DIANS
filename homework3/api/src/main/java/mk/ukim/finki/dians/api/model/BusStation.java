package mk.ukim.finki.dians.api.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bus_station")
public class BusStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String longitude;
    private String latitude;

}
