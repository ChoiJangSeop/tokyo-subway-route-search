package jangseop.tokyosubwayroutesearch.controller;


import jangseop.tokyosubwayroutesearch.domain.Route;
import jangseop.tokyosubwayroutesearch.domain.RouteType;
import jangseop.tokyosubwayroutesearch.domain.RouteUnit;
import jangseop.tokyosubwayroutesearch.service.RouteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(RouteController.class)
public class RouteControllerTest {

    @MockBean
    RouteService routeService;

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @DisplayName("경로를 조회합니다")
    public void getRoute() throws Exception {
        // given
        double testTotalDistance = 4.0;
        int testTotalDuration = 11;

        String testSrc = "station1";
        String testDest = "station2";

        int testUnitOrder = 1;
        String testUnitSrc = "station1";
        String testUnitDest = "station2";
        String testUnitLine = "G";
        int testUnitDuration = 11;
        double testUnitDistance = 4.0;

        when(routeService.findRoute(testSrc, testDest, RouteType.SHORT_DISTANCE)).thenReturn(
                new Route(
                        testSrc, testDest, RouteType.SHORT_DISTANCE,
                        testTotalDistance, testTotalDuration,
                        List.of(new RouteUnit(testUnitOrder, testUnitSrc, testUnitDest, testUnitLine, testUnitDistance,
                                testUnitDuration)))
        );

        
        // when
        mockMvc.perform(get(String.format("/routes?src=%s&dest=%s&type=%s", testSrc, testDest, RouteType.SHORT_DISTANCE.toString())))
                .andDo(print())
        // then
                .andExpect(jsonPath("$.totalDistance").value(is(testTotalDistance)))
                .andExpect(jsonPath("$.totalDuration").value(is(testTotalDuration)))
                .andExpect(jsonPath("$.units.length()").value(is(1)))
                .andExpect(jsonPath("$.units[0].order").value(is(testUnitOrder)))
                .andExpect(jsonPath("$.units[0].src").value(is(testUnitSrc)))
                .andExpect(jsonPath("$.units[0].dest").value(is(testUnitDest)))
                .andExpect(jsonPath("$.units[0].lineNumber").value(is(testUnitLine)))
                .andExpect(jsonPath("$.units[0].duration").value(is(testUnitDuration)))
                .andExpect(jsonPath("$.units[0].distance").value(is(testUnitDistance)));

    }
}
