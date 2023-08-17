package jangseop.tokyosubwayroutesearch.appservice;

import jangseop.tokyosubwayroutesearch.domain.Link;
import jangseop.tokyosubwayroutesearch.domain.Route;
import jangseop.tokyosubwayroutesearch.domain.RouteType;
import jangseop.tokyosubwayroutesearch.domain.RouteUnit;
import jangseop.tokyosubwayroutesearch.exception.RouteNotFoundException;
import jangseop.tokyosubwayroutesearch.service.LinkService;
import jangseop.tokyosubwayroutesearch.service.RouteService;
import jangseop.tokyosubwayroutesearch.service.RouteUnitService;
import jangseop.tokyosubwayroutesearch.service.dto.RouteCreateDto;
import jangseop.tokyosubwayroutesearch.service.dto.RouteUnitCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
public class RouteAppServiceImpl implements RouteAppService {

    private final RouteService routeService;

    private final LinkService linkService;

    private final RouteUnitService routeUnitService;

    @Override
    @Transactional(readOnly = true)
    public Route searchRoute(String src, String dest, RouteType routeType) {

        try {
            return routeService.findRoute(src, dest, routeType);
        } catch (RouteNotFoundException e) {
            return searchRouteByAlgorithm(src, dest, routeType);
        }
    }

    private Route searchRouteByAlgorithm(String src, String dest, RouteType routeType) {

        // TODO 최단경로 탐색 알고리즘 구현

        /**
         * 1. 다익스트라 알고리즘을 이용해 최단경로 찾음
         * 2. 해당 경로의 엣지(edge)의 리스트를 List<Link>로 리턴
         * 3. List<Link>를 기반, List<RouteUnit>으로 파싱
         * 4. Route, RouteUnit을 모두 생성
         */

        int initCapacity = 10;
        PriorityQueue<Node> pq = new PriorityQueue<>(initCapacity, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Double.compare(o1.data(), o2.data());
            }
        });

        Map<String, Double> dist = new HashMap<>();
        Map<String, List<Link>> links = new HashMap<>();
        Map<String, Boolean> visited = new HashMap<>();

        List<RouteUnit> routeUnits = new ArrayList<>();

        dist.put(src, 0D);
        links.put(src, new ArrayList<Link>());
        visited.put(src, true);

        // init
        linkService.findAllByStation(src).forEach(link -> {

            String targetStation = "";
            Double targetData = link.getData(routeType);
            List<Link> targetRoute = new ArrayList<>();
            targetRoute.add(link);

            if (link.prevStation().equals(src)) {
                targetStation = link.nextStation();
            } else {
                targetStation = link.prevStation();
            }


            dist.put(targetStation, targetData);
            links.put(targetStation, targetRoute);
            pq.add(new Node(targetStation, targetData));
        });

        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            visited.put(curr.station(), true);

            List<Link> currRoute = links.get(curr.station());

            if (curr.station().equals(dest)) {
                break;
            }

            linkService.findAllByStation(curr.station()).forEach(link -> {

                String targetStation = "";
                Double targetData = link.getData(routeType);

                if (link.prevStation().equals(curr.station())) {
                    targetStation = link.nextStation();
                } else {
                    targetStation = link.prevStation();
                }

                if (visited.get(targetStation) == null || !visited.get(targetStation)) {

                    if (dist.get(targetStation) == null || dist.get(targetStation) > curr.data() + targetData) {

                        dist.put(targetStation, curr.data() + targetData);

                        List<Link> targetRoute = new ArrayList<>(currRoute);
                        targetRoute.add(link);

                        links.put(targetStation, targetRoute);

                        pq.add(new Node(targetStation, curr.data() + targetData));
                    }
                }
            });

        }

        // TODO exception custom
        if (dist.get(dest) == null) throw new RuntimeException();

//        links.get(dest).forEach(link -> {
//            System.out.println((String.format("%s ~ %s : %f", link.prevStation(), link.nextStation(), link.distance())));
//        });

        // get shortest route!
        if (links.get(dest).size() == 1) {
            Link onlyLink = links.get(dest).get(0);
            routeUnits.add(new RouteUnit(1, src, dest, onlyLink.lineNumber(), onlyLink.distance(),
                    onlyLink.duration()));
        } else {
            routeUnits.addAll(mergeToRouteUnits(links.get(dest), routeType));
        }

        // create data to db

        int totalDuration = routeUnits.stream()
                .map(RouteUnit::duration)
                .reduce((x, y) -> x + y).orElse(0);

        double totalDistance = routeUnits.stream()
                .map(RouteUnit::distance)
                .reduce((x, y) -> x + y).orElse(0.0);

        Route newRoute = routeService.create(RouteCreateDto.of(src, dest, routeType, totalDistance, totalDuration));

        routeUnits.forEach(routeUnit -> {
            routeUnitService.create(
                    new RouteUnitCreateDto(routeUnit.order(), routeUnit.src(), routeUnit.dest(), routeUnit.lineNumber(),
                            routeUnit.distance(), routeUnit.duration(),  newRoute.id()));
        });

        return newRoute;
    }

    // List<Link> to List<RouteUnit>

    private List<RouteUnit> mergeToRouteUnits(List<Link> links, RouteType routeType) {

        List<RouteUnit> routeUnits = new ArrayList<>();


        int order = 1;
        int idx = 0;

        // TODO 구현방법 고민하기
        while (idx < links.size()) {
            int start = idx;
            int end = idx;

            double distance = links.get(idx).distance();
            int duration = links.get(idx).duration();

            while (idx < links.size() - 1) {

                if (links.get(idx).lineNumber().equals(links.get(idx+1).lineNumber())) {
                    end = ++idx;
                    distance += links.get(idx).distance();
                    duration += links.get(idx).duration();
                } else {
                    break;
                }
            }

            if (start != links.size() - 1) {
                if (links.get(start).nextStation().equals(links.get(start+1).prevStation())) {
                    routeUnits.add(new RouteUnit(order++, links.get(start).prevStation(), links.get(end).nextStation(),
                            links.get(start).lineNumber(), distance, duration));
                } else {
                    routeUnits.add(new RouteUnit(order++, links.get(start).nextStation(), links.get(end).prevStation(),
                            links.get(start).lineNumber(), distance, duration));
                }
            } else {
                if (links.get(start).nextStation().equals(links.get(start-1).prevStation())) {
                    routeUnits.add(new RouteUnit(order++, links.get(start).nextStation(), links.get(end).prevStation(),
                            links.get(start).lineNumber(), distance, duration));
                } else {
                    routeUnits.add(new RouteUnit(order++, links.get(start).prevStation(), links.get(end).nextStation(),
                            links.get(start).lineNumber(), distance, duration));
                }
            }

            idx++;
        }

        return routeUnits;
    }

    private record Node(String station, double data) {}


}
