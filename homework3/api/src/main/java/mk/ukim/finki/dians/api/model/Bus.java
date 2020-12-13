package mk.ukim.finki.dians.api.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bus")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

}
