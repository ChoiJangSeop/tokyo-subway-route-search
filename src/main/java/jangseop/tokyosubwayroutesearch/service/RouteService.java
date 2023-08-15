package jangseop.tokyosubwayroutesearch.service;

import jangseop.tokyosubwayroutesearch.domain.Route;
import jangseop.tokyosubwayroutesearch.domain.RouteType;
import jangseop.tokyosubwayroutesearch.service.dto.RouteCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface RouteService {

    Route create(RouteCreateDto dto);

    Route findRoute(String src, String dest, RouteType routeType);
}
