/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.viewmodel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Binh
 */

@Getter
@Setter
@ToString
public class FinishedGoodCreationFormData {

    private String name; 
    private String purchasePrice;
    private String sellingPrice;
    private String totalStock;
    private String cookable;
    private String finishedGoodCategory;
    
    private String rawMaterial1;
    private String rawMaterial1quantityneeded;
    private String rawMaterial2;
    private String rawMaterial2quantityneeded;
    private String rawMaterial3;
    private String rawMaterial3quantityneeded;
    private String rawMaterial4;
    private String rawMaterial4quantityneeded;
    private String rawMaterial5;
    private String rawMaterial5quantityneeded;
    private String rawMaterial6;
    private String rawMaterial6quantityneeded;
    private String rawMaterial7;
    private String rawMaterial7quantityneeded;
    
}
