/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.repository;

import com.senbazuru.inventory.model.FinishedGood;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author Binh
 */
public interface ShoppingCartInterface {

    void addProduct(FinishedGood product);

    void removeProduct(FinishedGood product);

    Map<FinishedGood, Integer> getProductsInCart();

    BigDecimal getTotal();

}
