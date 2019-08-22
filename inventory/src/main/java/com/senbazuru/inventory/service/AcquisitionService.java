/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.service;

import com.senbazuru.inventory.model.Acquisition;
import com.senbazuru.inventory.model.FinishedGood;
import com.senbazuru.inventory.repository.AcquistionRepository;
import com.senbazuru.inventory.repository.FinishedGoodRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Binh
 */
@Service
public class AcquisitionService {

    @Autowired
    private AcquistionRepository acqusitionRepository;

    public Acquisition saveAndFlushAcquisition(Acquisition acquisition) {
        return acqusitionRepository.saveAndFlush(acquisition);
    }

    public Acquisition saveAcquisition(Acquisition acquisition) {
        return acqusitionRepository.save(acquisition);
    }

    public Optional<Acquisition> findById(Long id) {
        return acqusitionRepository.findById(id);
    }

    public List<Acquisition> findAll() {
        return acqusitionRepository.findAll();
    }

}
