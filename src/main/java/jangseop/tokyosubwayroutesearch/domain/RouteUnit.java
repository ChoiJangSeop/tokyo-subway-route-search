package jangseop.tokyosubwayroutesearch.domain;

import jangseop.tokyosubwayroutesearch.entity.RouteUnitEntity;

public record RouteUnit(int order, String src, String dest, String lineNumber, double distance, int duration) {

    public static RouteUnit of(RouteUnitEntity routeUnitEntity) {
        return new RouteUnit(
                routeUnitEntity.getOrder(),
                routeUnitEntity.getSrc(), routeUnitEntity.getDest(), routeUnitEntity.getLineNumber(),
                routeUnitEntity.getDistance(), routeUnitEntity.getDuration());
    }
}
