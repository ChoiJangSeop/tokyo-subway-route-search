package jangseop.tokyosubwayroutesearch.service.dto;

import jangseop.tokyosubwayroutesearch.entity.RouteEntity;

public record RouteUnitCreateDto(int order, String src, String dest, String lineNumber, double distance, int duration,
                                 RouteEntity route) {

    public static RouteUnitCreateDto of(int order, String src, String dest, String lineNumber, double distance,
                                     int duration, RouteEntity routeEntity) {
        return new RouteUnitCreateDto(order, src, dest, lineNumber, distance, duration, routeEntity);
    }
}
