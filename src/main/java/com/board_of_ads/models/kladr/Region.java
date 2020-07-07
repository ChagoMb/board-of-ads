package com.board_of_ads.models.kladr;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "regions")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 40)
    private String name;

    @Column(name = "socr", length = 10)
    private String shortType;

    @Column(name = "indx", length = 6)
    private String index;

    @Column(name = "gninmb", length = 4)
    private String gninmb;

    @Column(name = "uno", length = 4)
    private String uno;

    @Column(name = "ocatd", length = 11)
    private String ocatd;

    @Column(name = "code", length = 13)
    private String code;

}
