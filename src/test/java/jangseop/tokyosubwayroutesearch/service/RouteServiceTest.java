package jangseop.tokyosubwayroutesearch.service;


import jangseop.tokyosubwayroutesearch.domain.Route;
import jangseop.tokyosubwayroutesearch.domain.RouteType;
import jangseop.tokyosubwayroutesearch.domain.RouteUnit;
import jangseop.tokyosubwayroutesearch.entity.RouteEntity;
import jangseop.tokyosubwayroutesearch.entity.RouteUnitEntity;
import jangseop.tokyosubwayroutesearch.exception.RouteNotFoundException;
import jangseop.tokyosubwayroutesearch.repository.RouteRepository;
import jangseop.tokyosubwayroutesearch.repository.RouteUnitRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.util.Lists.emptyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RouteServiceTest {
    
    @Test
    @DisplayName("경로를 조회합니다")
    public void findRoute() throws Exception {
        // given
        RouteEntity routeEntity = mock(RouteEntity.class);
        RouteUnitEntity routeUnitEntity = mock(RouteUnitEntity.class);

        Long testRouteId = 1L;
        String testSrc = "src";
        String testDest = "dest";
        RouteType testRouteType = RouteType.SHORT_DISTANCE;
        double testTotalDistance = 0.0;
        int testTotalDuration = 0;

        when(routeEntity.getSrc()).thenReturn(testSrc);
        when(routeEntity.getDest()).thenReturn(testDest);
        when(routeEntity.getRouteType()).thenReturn(testRouteType);
        when(routeEntity.getTotalDistance()).thenReturn(testTotalDistance);
        when(routeEntity.getTotalDuration()).thenReturn(testTotalDuration);
        when(routeEntity.getRoutes()).thenReturn(List.of(routeUnitEntity));


        int testOrder = 1;
        String testUnitSrc = "unit src";
        String testUnitDest = "unit dest";
        String testUnitLineNumber = "L";
        double distance = 0.0;
        int duration = 0;

        when(routeUnitEntity.getOrder()).thenReturn(testOrder);
        when(routeUnitEntity.getSrc()).thenReturn(testUnitSrc);
        when(routeUnitEntity.getDest()).thenReturn(testUnitDest);
        when(routeUnitEntity.getLineNumber()).thenReturn(testUnitLineNumber);
        when(routeUnitEntity.getDistance()).thenReturn(distance);
        when(routeUnitEntity.getDuration()).thenReturn(duration);

        RouteRepository routeRepository = mock(RouteRepository.class);
        RouteUnitRepository routeUnitRepository = mock(RouteUnitRepository.class);

        when(routeRepository.findBySrc(testSrc)).thenReturn(List.of(routeEntity));

        // when
        RouteService routeService = new RouteServiceImpl(routeRepository, routeUnitRepository);
        Route findRoute = routeService.findRoute(testSrc, testDest, testRouteType);

        // then
        assertThat(findRoute.src()).isEqualTo(testSrc);
        assertThat(findRoute.dest()).isEqualTo(testDest);
        assertThat(findRoute.routeType()).isEqualTo(testRouteType);
        assertThat(findRoute.routes().size()).isEqualTo(1);
        assertThat(findRoute.routes().get(0).src()).isEqualTo(testUnitSrc);
        assertThat(findRoute.routes().get(0).dest()).isEqualTo(testUnitDest);
        assertThat(findRoute.routes().get(0).lineNumber()).isEqualTo(testUnitLineNumber);
        assertThat(findRoute.routes().get(0).distance()).isEqualTo(distance);
        assertThat(findRoute.routes().get(0).duration()).isEqualTo(duration);
    }

    @Test
    @DisplayName("조회할 경로가 없으면 예외를 던집니다")
    public void handleRouteNotFoundException() throws Exception {
        // given
        String testSrc = "src";

        RouteRepository routeRepository = mock(RouteRepository.class);
        RouteUnitRepository routeUnitRepository = mock(RouteUnitRepository.class);

        when(routeRepository.findBySrc(testSrc)).thenReturn(List.of());

        // when
        RouteService routeService = new RouteServiceImpl(routeRepository, routeUnitRepository);

        // then
        assertThatThrownBy(() -> routeService.findRoute(testSrc, testSrc, RouteType.SHORT_DISTANCE))
                .isInstanceOf(RouteNotFoundException.class);
    }

}
