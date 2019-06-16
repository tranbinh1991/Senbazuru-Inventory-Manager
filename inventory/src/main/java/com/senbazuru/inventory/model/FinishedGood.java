/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.model;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @OneToMany
    private List<RawMaterial> rawMaterialList;
    

    @OneToMany(mappedBy = "finishedGood")
    private List<FinishedGoodAcquisition> finishedGoodAcquisitionList;

    @OneToMany(mappedBy = "finishedGood")
    private List<Sale> saleList;

    private BigDecimal sellingPrice;
    private BigDecimal purchasePrice;
    private FinishedGoodCategory finishedGoodCategory;

}