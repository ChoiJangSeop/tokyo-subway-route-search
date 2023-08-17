package jangseop.tokyosubwayroutesearch.repository;

import jangseop.tokyosubwayroutesearch.entity.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepsoitory extends JpaRepository<LinkEntity, Long> {

    List<LinkEntity> findByPrevStation(String stationNAme);

    List<LinkEntity> findByNextStation(String stationName);
}
