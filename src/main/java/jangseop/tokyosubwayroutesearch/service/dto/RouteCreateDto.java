package jangseop.tokyosubwayroutesearch.service.dto;

import jangseop.tokyosubwayroutesearch.domain.RouteType;

public record RouteCreateDto(String src, String dest, RouteType routeType, double totalDistance, int totalDuration) {

    public static RouteCreateDto of(String src, String dest, RouteType routeType, double totalDistance,
                                    int totalDuration) {
        return new RouteCreateDto(src, dest, routeType, totalDistance, totalDuration);
    }
}
