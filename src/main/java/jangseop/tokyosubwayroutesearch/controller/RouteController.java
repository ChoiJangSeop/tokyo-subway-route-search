package jangseop.tokyosubwayroutesearch.controller;


import jangseop.tokyosubwayroutesearch.appservice.RouteAppService;
import jangseop.tokyosubwayroutesearch.controller.dto.RouteResponse;
import jangseop.tokyosubwayroutesearch.domain.Route;
import jangseop.tokyosubwayroutesearch.domain.RouteType;
import jangseop.tokyosubwayroutesearch.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class RouteController {

    private final RouteService routeService;

    private final RouteAppService routeAppService;

    @GetMapping("/routes")
    public ResponseEntity<RouteResponse> getOne(@RequestParam("src") String src,
                                                @RequestParam("dest") String dest,
                                                @RequestParam("type") String type) {

        Route route = routeAppService.searchRoute(src, dest, RouteType.valueOf(type));
        return new ResponseEntity<>(RouteResponse.of(route), HttpStatus.OK);
    }
}
