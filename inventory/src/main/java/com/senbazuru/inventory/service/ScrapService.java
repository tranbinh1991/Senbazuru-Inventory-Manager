/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.service;

import com.senbazuru.inventory.model.Acquisition;
import com.senbazuru.inventory.model.FinishedGood;
import com.senbazuru.inventory.model.Scrap;
import com.senbazuru.inventory.repository.AcquistionRepository;
import com.senbazuru.inventory.repository.FinishedGoodRepository;
import com.senbazuru.inventory.repository.ScrapRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Binh
 */
@Service
public class ScrapService {

    @Autowired
    private ScrapRepository scrapRepository;

    public Scrap saveAndFlushAcquisition(Scrap scrap) {
        return scrapRepository.saveAndFlush(scrap);
    }

    public Scrap saveAcquisition(Scrap scrap) {
        return scrapRepository.save(scrap);
    }

    public Optional<Scrap> findById(Long id) {
        return scrapRepository.findById(id);
    }

    public List<Scrap> findAll() {
        return scrapRepository.findAll();
    }

}
