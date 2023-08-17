package jangseop.tokyosubwayroutesearch.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "LINK")
public class LinkEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LINK_ID")
    private Long id;

    private String prevStation;

    private String nextStation;

    private String lineNumber;

    private double distance;

    private int duration;
}
