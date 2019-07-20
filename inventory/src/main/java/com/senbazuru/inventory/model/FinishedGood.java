/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Binh
 */
@Setter
@Getter
@Entity
public class FinishedGood {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer totalStock;
    private String name;
    private Boolean cookable;


    @OneToMany(mappedBy = "finishedGood")
    private List<FinishedGoodAcquisition> finishedGoodAcquisitionList;

    @OneToMany(mappedBy = "finishedGood")
    private List<Sale> saleList;

    private BigDecimal sellingPrice;
    private BigDecimal purchasePrice;

    @ManyToMany(mappedBy = "finishedGoodList")
    private List<FinishedGoodCategory> finishedGoodCategory;

    @ElementCollection
    @CollectionTable(name = "rawmaterial_quantity",
            joinColumns = {
                @JoinColumn(name = "finished_good_id")})
    @MapKeyColumn(name = "rawmaterial")
    @Column(name = "quantity")
    private Map<RawMaterial, Integer> itemQuantityMap;

}
