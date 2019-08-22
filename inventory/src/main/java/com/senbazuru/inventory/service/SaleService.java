/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.service;

import com.senbazuru.inventory.model.FinishedGood;
import com.senbazuru.inventory.model.Sale;
import com.senbazuru.inventory.repository.FinishedGoodRepository;
import com.senbazuru.inventory.repository.SaleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Binh
 */
@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    public Sale saveSale(Sale sale) {
        return saleRepository.saveAndFlush(sale);
    }

    public Optional<Sale> findById(Long id) {
        return saleRepository.findById(id);
    }

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

}
