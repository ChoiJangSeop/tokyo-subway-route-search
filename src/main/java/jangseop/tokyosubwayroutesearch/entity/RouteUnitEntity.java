package jangseop.tokyosubwayroutesearch.entity;

import jakarta.persistence.*;
import jangseop.tokyosubwayroutesearch.service.dto.RouteUnitCreateDto;
import lombok.Getter;

@Entity
@Getter
@Table(name = "ROUTE_UNIT")
public class RouteUnitEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROUTE_UNIT_ID")
    private Long id;

    private int number;

    private String src;

    private String dest;

    private String lineNumber;

    private double distance;

    private int duration;

    @ManyToOne
    @JoinColumn(name = "ROUTE_ID")
    private RouteEntity route;

    /**
     * creation method
     */
    public static RouteUnitEntity of(RouteUnitCreateDto dto) {
        RouteUnitEntity routeUnitEntity = new RouteUnitEntity();

        routeUnitEntity.number = dto.order();
        routeUnitEntity.src = dto.src();
        routeUnitEntity.dest = dto.dest();
        routeUnitEntity.lineNumber = dto.lineNumber();
        routeUnitEntity.distance = dto.distance();
        routeUnitEntity.duration = dto.duration();

        return routeUnitEntity;
    }

    public void setRoute(RouteEntity route) {
        if (this.route != null) {
            this.route.getRoutes().remove(this);
        }

        this.route = route;
        route.getRoutes().add(this);
    }

}

