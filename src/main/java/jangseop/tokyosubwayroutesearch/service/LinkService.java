package jangseop.tokyosubwayroutesearch.service;

import jangseop.tokyosubwayroutesearch.domain.Link;

import java.util.List;

public interface LinkService {

    List<Link> findAllByStation(String stationName);
}
