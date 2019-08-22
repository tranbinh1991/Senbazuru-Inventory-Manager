/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Binh
 */

@Getter
@Setter
public class OrderDTO {
    
    private String productName;
    private Integer quantity;
}
