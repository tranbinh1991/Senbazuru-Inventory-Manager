/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.service;

import com.senbazuru.inventory.model.FinishedGood;
import com.senbazuru.inventory.repository.FinishedGoodRepository;
import com.senbazuru.inventory.repository.ShoppingCartInterface;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.print.attribute.standard.Fidelity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Binh
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class TableSessionService implements ShoppingCartInterface {

    private Long currentTable = null;

    public Long getCurrentTable() {
        return currentTable;
    }

    public void setCurrentTable(Long currentTable) {
        this.currentTable = currentTable;
    }

    private final FinishedGoodRepository productRepository;

    private Map<FinishedGood, Integer> products = new HashMap<>();
    private Map<FinishedGood, Integer> products1 = new HashMap<>();
    private Map<FinishedGood, Integer> products2 = new HashMap<>();
    private Map<FinishedGood, Integer> products3 = new HashMap<>();
    private Map<FinishedGood, Integer> products4 = new HashMap<>();
    private Map<FinishedGood, Integer> products5 = new HashMap<>();

    @Autowired
    public TableSessionService(FinishedGoodRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * If product is in the map just increment quantity by 1. If product is not
     * in the map with, add it with quantity 1
     *
     * @param product
     *
     */
    @Override
    public void addProduct(FinishedGood product) {
        Map<FinishedGood, Integer> currectProducts = findTable(currentTable);

        if (currectProducts.isEmpty()) {
            currectProducts.put(product, 1);
        } else {
            boolean foundProduct = false;
            for (Map.Entry<FinishedGood, Integer> entry : currectProducts.entrySet()) {
                if (entry.getKey().getName().equals(product.getName())) {
                    entry.setValue(entry.getValue() + 1);
                    foundProduct = true;
                    
                }
            }
            if (foundProduct == false) {
                currectProducts.put(product, 1);
            }

        }

    }

    /**
     * If product is in the map with quantity > 1, just decrement quantity by 1.
     * If product is in the map with quantity 1, remove it from map
     *
     * @param product
     *
     */
    @Override
    public void removeProduct(FinishedGood product) {
        Map<FinishedGood, Integer> currectProducts = findTable(currentTable);

        for (Iterator<Map.Entry<FinishedGood, Integer>> it = currectProducts.entrySet().iterator(); it.hasNext();) {
            Map.Entry<FinishedGood, Integer> entry = it.next();
            if (entry.getKey().getName().equals(product.getName())) {
                it.remove();
            }
        }

    }

    @Override
    public Map<FinishedGood, Integer> getProductsInCart() {
        Map<FinishedGood, Integer> currectProducts = findTable(currentTable);
        return currectProducts;
    }

    @Override
    public BigDecimal getTotal() {
        Map<FinishedGood, Integer> currectProducts = findTable(currentTable);
        return currectProducts.entrySet().stream()
                .map(entry -> entry.getKey().getSellingPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public Map<FinishedGood, Integer> findTable(Long tableId) {
        if (tableId == 1) {
            return products;
        } else if (tableId == 2) {
            return products1;

        } else if (tableId == 3) {
            return products2;

        } else if (tableId == 4) {
            return products3;

        } else if (tableId == 5) {
            return products4;

        } else {
            return products5;
        }
    }

}
