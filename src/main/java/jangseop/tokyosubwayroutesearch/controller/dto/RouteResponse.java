package jangseop.tokyosubwayroutesearch.controller.dto;

import jangseop.tokyosubwayroutesearch.domain.Route;
import jangseop.tokyosubwayroutesearch.domain.RouteUnit;

import java.util.List;

public record RouteResponse(String src, String dest, Double totalDistance, int totalDuration, List<RouteUnit> units) {

    public static RouteResponse of(Route route) {
        return new RouteResponse(
                route.src(), route.dest(),
                route.totalDistance(), route.totalDuration(),
                route.routes());
    }

}
