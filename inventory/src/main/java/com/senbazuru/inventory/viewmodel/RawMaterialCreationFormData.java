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
public class RawMaterialCreationFormData {

    private String name; 
    private String purchasePrice;
    private String totalStock;
    private String imageLink;
    private String minimumStock;
}
