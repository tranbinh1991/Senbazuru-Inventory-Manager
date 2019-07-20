/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.model;

/**
 *
 * @author Binh
 */
public enum Category {
    DRINK("Ital"), 
    SUSHI("Sushi"), 
    PHO("Pho"), 
    WOK("Wok");

    private String displayValue;
     

    Category(String displayValue) {
        this.displayValue = displayValue;
    }
     
    public String getDisplayValue() {
        return displayValue;
    }
}
