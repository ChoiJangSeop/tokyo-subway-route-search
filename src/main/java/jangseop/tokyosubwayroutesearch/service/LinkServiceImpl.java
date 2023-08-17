package jangseop.tokyosubwayroutesearch.service;

import jangseop.tokyosubwayroutesearch.domain.Link;
import jangseop.tokyosubwayroutesearch.repository.LinkRepsoitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final LinkRepsoitory linkRepsoitory;

    @Override
    public List<Link> findAllByStation(String stationName) {
        List<Link> prevLinks = linkRepsoitory.findByPrevStation(stationName).stream()
                .map(Link::of)
                .toList();

        List<Link> nextLinks = linkRepsoitory.findByNextStation(stationName).stream()
                .map(Link::of)
                .toList();

        List<Link> links = new ArrayList<>(prevLinks);
        links.addAll(nextLinks);

        return links;
    }
}
