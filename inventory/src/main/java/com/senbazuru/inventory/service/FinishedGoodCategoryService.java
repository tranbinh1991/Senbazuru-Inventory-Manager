/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.service;

import com.senbazuru.inventory.model.Category;
import com.senbazuru.inventory.model.FinishedGoodCategory;
import com.senbazuru.inventory.repository.FinishedGoodCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Binh
 */
@Service
public class FinishedGoodCategoryService {
     
    
    @Autowired
    FinishedGoodCategoryRepository finishedGoodCategoryRepository;
    
    public FinishedGoodCategory saveFinishedGoodCategory(FinishedGoodCategory finishedGoodCategory){
        return finishedGoodCategoryRepository.saveAndFlush(finishedGoodCategory);
    }
    
    public void saveAllCategories(){
        for (int i = 0; i < Category.values().length; i++) {
            FinishedGoodCategory fgc = new FinishedGoodCategory();
            fgc.setCategory(Category.values()[i]);
            saveFinishedGoodCategory(fgc);           
        }
    }
}
