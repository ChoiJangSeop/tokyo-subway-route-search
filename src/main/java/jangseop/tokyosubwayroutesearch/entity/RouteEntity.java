package jangseop.tokyosubwayroutesearch.entity;

import jakarta.persistence.*;
import jangseop.tokyosubwayroutesearch.domain.RouteType;
import jangseop.tokyosubwayroutesearch.service.dto.RouteCreateDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "ROUTE")
public class RouteEntity {

    @Id @GeneratedValue
    @Column(name = "ROUTE_ID")
    private Long id;

    private String src;

    private String dest;

    @Enumerated(EnumType.STRING)
    private RouteType routeType;

    private double totalDistance;

    private int totalDuration;

    @OneToMany(mappedBy = "route")
    private List<RouteUnitEntity> routes = new ArrayList<>();

    /**
     * creation method
     */
    public static RouteEntity of(RouteCreateDto dto) {
        RouteEntity routeEntity = new RouteEntity();
        routeEntity.src = dto.src();
        routeEntity.dest = dto.dest();
        routeEntity.routeType = dto.routeType();
        routeEntity.totalDistance = dto.totalDistance();
        routeEntity.totalDuration = dto.totalDuration();

        return routeEntity;
    }

    // TODO 연관관계 편의 메서드

    public void addRouteUnit(RouteUnitEntity unit) {
        this.routes.add(unit);
    }

    public void removeRouteUnit(RouteUnitEntity unit) {
        this.routes.remove(unit);
    }
}
