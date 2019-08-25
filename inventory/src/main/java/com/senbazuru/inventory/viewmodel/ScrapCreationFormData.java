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
public class ScrapCreationFormData {
    
    private String reason;
    private String sellingPrice;

    private String finishedGood1;
    private String finishedGood1quantityneeded;
    private String finishedGood2;
    private String finishedGood2quantityneeded;
    private String finishedGood3;
    private String finishedGood3quantityneeded;
    private String finishedGood4;
    private String finishedGood4quantityneeded;
    private String finishedGood5;
    private String finishedGood5quantityneeded;
    private String finishedGood6;
    private String finishedGood6quantityneeded;
    private String finishedGood7;
    private String finishedGood7quantityneeded;

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
