/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.repository;

import com.senbazuru.inventory.model.FinishedGood;
import com.senbazuru.inventory.model.Acquisition;
import com.senbazuru.inventory.model.Sale;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Binh
 */
@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>{
    List<Sale> findAll();
    Optional<Sale> findById(Long id);
    
    
}
