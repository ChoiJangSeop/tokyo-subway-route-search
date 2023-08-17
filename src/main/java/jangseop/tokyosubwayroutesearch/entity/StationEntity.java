package jangseop.tokyosubwayroutesearch.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "STATION")
public class StationEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATION_ID")
    private Long id;

    private String name;
}
