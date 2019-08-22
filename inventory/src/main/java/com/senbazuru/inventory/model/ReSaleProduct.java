/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.model;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
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
public class ReSaleProduct extends FinishedGood {

    private Integer totalStock;
    private Integer minimumStock;
    
    private BigDecimal purchasePrice;
}
