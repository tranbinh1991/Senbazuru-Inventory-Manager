/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.repository;

import com.senbazuru.inventory.model.FinishedGood;
import com.senbazuru.inventory.model.Category;
import com.senbazuru.inventory.model.FinishedGoodCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Binh
 */
@Repository
public interface FinishedGoodCategoryRepository extends JpaRepository<FinishedGoodCategory, Long>{
    
    public List<FinishedGoodCategory> findByCategory(Category category);
    
    
}
