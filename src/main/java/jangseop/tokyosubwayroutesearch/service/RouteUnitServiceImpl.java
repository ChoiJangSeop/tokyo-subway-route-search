package jangseop.tokyosubwayroutesearch.service;

import jangseop.tokyosubwayroutesearch.domain.RouteUnit;
import jangseop.tokyosubwayroutesearch.entity.RouteEntity;
import jangseop.tokyosubwayroutesearch.entity.RouteUnitEntity;
import jangseop.tokyosubwayroutesearch.exception.DataNotFoundException;
import jangseop.tokyosubwayroutesearch.repository.RouteRepository;
import jangseop.tokyosubwayroutesearch.repository.RouteUnitRepository;
import jangseop.tokyosubwayroutesearch.service.dto.RouteUnitCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteUnitServiceImpl implements RouteUnitService {

    private final RouteUnitRepository routeUnitRepository;

    private final RouteRepository routeRepository;

    @Override
    public RouteUnit create(RouteUnitCreateDto dto) {
        RouteUnitEntity routeUnitEntity = RouteUnitEntity.of(dto);

        RouteEntity routeEntity = routeRepository.findById(dto.routeId())
                .orElseThrow(() -> new DataNotFoundException(dto.routeId()));

        routeUnitEntity.setRoute(routeEntity);

        routeUnitRepository.save(routeUnitEntity);

        return RouteUnit.of(routeUnitEntity);
    }

    @Override
    public List<RouteUnit> findByRoute(Long routeId) {
        return routeUnitRepository.findByRoute(routeRepository.getReferenceById(routeId)).stream()
                .map(RouteUnit::of)
                .toList();
    }
}
