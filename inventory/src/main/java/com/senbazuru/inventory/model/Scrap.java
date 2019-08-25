/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Binh
 */
@Entity
@Setter
@Getter
public class Scrap {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private BigDecimal sellingPrice;
    private BigDecimal totalCost;
    private String reason;

    private LocalDateTime localDateTime;

    @ElementCollection
    @CollectionTable(name = "finished_good_scrapping_quantity",
            joinColumns = {
                @JoinColumn(name = "scrap_id")})
    @MapKeyColumn(name = "finished_good")
    @Column(name = "quantity")
    private Map<FinishedGood, Integer> finishedGoodQuantityMap;

    @ElementCollection
    @CollectionTable(name = "raw_material_scrapping_quantity",
            joinColumns = {
                @JoinColumn(name = "scrap_id")})
    @MapKeyColumn(name = "raw_material")
    @Column(name = "quantity")
    private Map<RawMaterial, Integer> rawMaterialQuantityMap;
}
