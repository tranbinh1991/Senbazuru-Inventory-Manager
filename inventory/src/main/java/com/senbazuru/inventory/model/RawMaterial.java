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

/**
 *
 * @author Binh
 */
@Entity
public class RawMaterial {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    private String name;
    
    private BigDecimal purchasePrice;
    
    private Integer totalStock;
    
    @OneToMany(mappedBy = "rawMaterial")
    private List<RawAcquisition> rawMaterialQcquisitionList;
            
}
