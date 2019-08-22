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
public class AcquisitionCreationFormData {

    private String reSaleProduct1;
    private String reSaleProduct1quantityneeded;
    private String reSaleProduct2;
    private String reSaleProduct2quantityneeded;
    private String reSaleProduct3;
    private String reSaleProduct3quantityneeded;
    private String reSaleProduct4;
    private String reSaleProduct4quantityneeded;
    private String reSaleProduct5;
    private String reSaleProduct5quantityneeded;
    private String reSaleProduct6;
    private String reSaleProduct6quantityneeded;
    private String reSaleProduct7;
    private String reSaleProduct7quantityneeded;

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
