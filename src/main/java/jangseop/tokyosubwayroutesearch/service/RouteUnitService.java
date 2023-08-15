package jangseop.tokyosubwayroutesearch.service;

import jangseop.tokyosubwayroutesearch.domain.RouteUnit;
import jangseop.tokyosubwayroutesearch.service.dto.RouteUnitCreateDto;

import java.util.List;

public interface RouteUnitService {

    RouteUnit create(RouteUnitCreateDto dto);

    List<RouteUnit> findByRoute(Long routeId);
}
