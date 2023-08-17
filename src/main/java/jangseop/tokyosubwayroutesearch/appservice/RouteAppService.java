package jangseop.tokyosubwayroutesearch.appservice;


import jangseop.tokyosubwayroutesearch.domain.Route;
import jangseop.tokyosubwayroutesearch.domain.RouteType;

public interface RouteAppService {

    Route searchRoute(String src, String dest, RouteType routeType);
}
