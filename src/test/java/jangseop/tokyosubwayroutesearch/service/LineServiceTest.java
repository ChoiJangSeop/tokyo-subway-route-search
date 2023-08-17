package jangseop.tokyosubwayroutesearch.service;

import jangseop.tokyosubwayroutesearch.domain.Link;
import jangseop.tokyosubwayroutesearch.entity.LinkEntity;
import jangseop.tokyosubwayroutesearch.repository.LinkRepsoitory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LineServiceTest {

    @Test
    @DisplayName("역이름을 통해 모든 링크를 조회합니다.")
    public void findByStation() throws Exception {
        // given
        LinkEntity linkEntity1 = mock(LinkEntity.class);
        LinkEntity linkEntity2 = mock(LinkEntity.class);
        
        Long testLinkId1 = 1L;
        Long testLinkId2 = 2L;
        
        String testPrevStation1 = "station1";
        String testPrevStation2 = "station2";
        
        String testNextStation1 = "station3";
        String testNextStation2 = "station1";
        
        String testLineNumber1 = "T";
        String testLineNumber2 = "G";
        
        double testDistance1 = 0.0;
        double testDistance2 = 1.0;
        
        int testDuration1 = 1;
        int testDuration2 = 1;
        
        when(linkEntity1.getId()).thenReturn(testLinkId1);
        when(linkEntity1.getPrevStation()).thenReturn(testPrevStation1);
        when(linkEntity1.getNextStation()).thenReturn(testNextStation1);
        when(linkEntity1.getLineNumber()).thenReturn(testLineNumber1);
        when(linkEntity1.getDistance()).thenReturn(testDistance1);
        when(linkEntity1.getDuration()).thenReturn(testDuration1);

        when(linkEntity2.getId()).thenReturn(testLinkId2);
        when(linkEntity2.getPrevStation()).thenReturn(testPrevStation2);
        when(linkEntity2.getNextStation()).thenReturn(testNextStation2);
        when(linkEntity2.getLineNumber()).thenReturn(testLineNumber2);
        when(linkEntity2.getDistance()).thenReturn(testDistance2);
        when(linkEntity2.getDuration()).thenReturn(testDuration2);

        LinkRepsoitory linkRepsoitory = mock(LinkRepsoitory.class);
        when(linkRepsoitory.findByNextStation(testNextStation2)).thenReturn(List.of(linkEntity2));
        when(linkRepsoitory.findByPrevStation(testPrevStation1)).thenReturn(List.of(linkEntity1));

        // when
        LinkService linkService = new LinkServiceImpl(linkRepsoitory);
        List<Link> links = linkService.findAllByStation(testPrevStation1);

        // then
        assertThat(links.size()).isEqualTo(2);
        assertThat(links.get(0).id()).isEqualTo(testLinkId1);
        assertThat(links.get(0).prevStation()).isEqualTo(testPrevStation1);
        assertThat(links.get(0).nextStation()).isEqualTo(testNextStation1);
        assertThat(links.get(0).lineNumber()).isEqualTo(testLineNumber1);
        assertThat(links.get(0).distance()).isEqualTo(testDistance1);
        assertThat(links.get(0).duration()).isEqualTo(testDuration2);
    }
}
