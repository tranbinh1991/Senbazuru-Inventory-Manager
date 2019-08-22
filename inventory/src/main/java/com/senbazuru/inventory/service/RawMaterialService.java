/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.service;

import com.senbazuru.inventory.model.RawMaterial;
import com.senbazuru.inventory.repository.RawMaterialRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Binh
 */
@Service
public class RawMaterialService {

    @Autowired
    RawMaterialRepository rawMaterialRepository;

    public RawMaterial saveAndFlushRawMaterial(RawMaterial rawMaterial) {
        return rawMaterialRepository.saveAndFlush(rawMaterial);
    }

    public RawMaterial saveRawMaterial(RawMaterial rawMaterial) {
        return rawMaterialRepository.save(rawMaterial);
    }

    public List<RawMaterial> findAll() {
        return rawMaterialRepository.findAll();
    }

    public List<RawMaterial> findByName(String name) {
        return rawMaterialRepository.findByName(name);
    }
    
    public RawMaterial findFirstById(Long id){
        return rawMaterialRepository.findFirstById(id);
    }
}
