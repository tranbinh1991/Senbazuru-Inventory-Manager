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
import javax.persistence.FetchType;
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
@Setter
@Getter
@Entity
public class Acquisition {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private BigDecimal totalPurchasePrice;

    private LocalDateTime localDateTime;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "re_sale_product_quantity",
            joinColumns = {
                @JoinColumn(name = "acquisition_id")})
    @MapKeyColumn(name = "re_sale_product")
    @Column(name = "quantity")
    private Map<ReSaleProduct, Integer> reSaleProductQuantityMap;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "raw_material_acquisition_quantity",
            joinColumns = {
                @JoinColumn(name = "acquisition_id")})
    @MapKeyColumn(name = "raw_material")
    @Column(name = "quantity")
    private Map<RawMaterial, Integer> rawMaterialQuantityMap;
}
