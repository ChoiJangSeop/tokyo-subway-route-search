package jangseop.tokyosubwayroutesearch.domain;

import jangseop.tokyosubwayroutesearch.entity.LinkEntity;

public record Link(Long id, String prevStation, String nextStation, String lineNumber, double distance, int duration) {

    public static Link of(LinkEntity linkEntity) {
        return new Link(
                linkEntity.getId(),
                linkEntity.getPrevStation(), linkEntity.getNextStation(),
                linkEntity.getLineNumber(),
                linkEntity.getDistance(), linkEntity.getDuration()
        );
    }

    public Double getData(RouteType routeType) {
        if (routeType == RouteType.SHORT_DISTANCE) {
            return this.distance();
        } else {
            return (double) this.duration();
        }
    }
}
