package jangseop.tokyosubwayroutesearch.repository;

import jangseop.tokyosubwayroutesearch.entity.RouteEntity;
import jangseop.tokyosubwayroutesearch.entity.RouteUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteUnitRepository extends JpaRepository<RouteUnitEntity, Long> {

    List<RouteUnitEntity> findByRoute(RouteEntity route);
}
