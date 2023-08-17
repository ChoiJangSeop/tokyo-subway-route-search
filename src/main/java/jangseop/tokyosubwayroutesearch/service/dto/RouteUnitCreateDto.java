package jangseop.tokyosubwayroutesearch.service.dto;

import jangseop.tokyosubwayroutesearch.entity.RouteEntity;

public record RouteUnitCreateDto(int order, String src, String dest, String lineNumber, double distance, int duration,
                                 Long routeId) {

    public static RouteUnitCreateDto of(int order, String src, String dest, String lineNumber, double distance,
                                     int duration, Long routeId) {
        return new RouteUnitCreateDto(order, src, dest, lineNumber, distance, duration, routeId);
    }
}
