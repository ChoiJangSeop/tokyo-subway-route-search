package jangseop.tokyosubwayroutesearch.domain;

import jangseop.tokyosubwayroutesearch.entity.RouteEntity;

import java.util.List;

public record Route(String src, String dest, RouteType routeType, double totalDistance, int totalDuration,
                    List<RouteUnit> routes) {

    public static Route of(RouteEntity routeEntity) {
        return new Route(
                routeEntity.getSrc(), routeEntity.getDest(), routeEntity.getRouteType(),
                routeEntity.getTotalDistance(), routeEntity.getTotalDuration(),
                routeEntity.getRoutes().stream()
                        .map(RouteUnit::of)
                        .toList()
        );
    }
}
