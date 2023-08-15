package jangseop.tokyosubwayroutesearch.service;

import jangseop.tokyosubwayroutesearch.domain.Route;
import jangseop.tokyosubwayroutesearch.domain.RouteType;
import jangseop.tokyosubwayroutesearch.entity.RouteEntity;
import jangseop.tokyosubwayroutesearch.exception.RouteNotFoundException;
import jangseop.tokyosubwayroutesearch.repository.RouteRepository;
import jangseop.tokyosubwayroutesearch.repository.RouteUnitRepository;
import jangseop.tokyosubwayroutesearch.service.dto.RouteCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final RouteUnitRepository routeUnitRepository;

    @Override
    public Route create(RouteCreateDto dto) {
        RouteEntity routeEntity = RouteEntity.of(dto);
        routeRepository.save(routeEntity);

        return Route.of(routeEntity);
    }

    @Override
    public Route findRoute(String src, String dest, RouteType routeType) {

        List<Route> routes = routeRepository.findBySrc(src).stream()
                .filter(route -> route.getDest().equals(dest) && route.getRouteType().equals(routeType))
                .map(Route::of)
                .toList();

        if (routes.isEmpty()) throw new RouteNotFoundException(src, dest, routeType);

        return routes.get(0);
    }
}
