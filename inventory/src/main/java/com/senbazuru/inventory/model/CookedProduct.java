/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.model;

import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
public class CookedProduct extends FinishedGood {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "rawmaterial_quantity",
            joinColumns = {
                @JoinColumn(name = "cooked_product_id")})
    @MapKeyColumn(name = "rawmaterial")
    @Column(name = "quantity")
    private Map<RawMaterial, Integer> itemQuantityMap;
}
