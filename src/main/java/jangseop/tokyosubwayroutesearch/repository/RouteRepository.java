package jangseop.tokyosubwayroutesearch.repository;

import jangseop.tokyosubwayroutesearch.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<RouteEntity, Long> {

    List<RouteEntity> findBySrc(String src);

    List<RouteEntity> findByDest(String dest);
}
