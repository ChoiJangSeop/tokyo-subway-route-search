package jangseop.tokyosubwayroutesearch.exception;

import jangseop.tokyosubwayroutesearch.domain.RouteType;

public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException(String src, String dest, RouteType routeType) {
        super(String.format("(%s) : (%s) ~ (%s) route is not found.",
                routeType.toString(), src, dest));
    }
}
