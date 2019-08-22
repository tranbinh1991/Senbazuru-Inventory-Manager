/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.service;

import com.senbazuru.inventory.model.FinishedGood;
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
public class FinishedGoodService {

    @Autowired
    private FinishedGoodRepository finishedGoodRepository;

    public FinishedGood saveAndFlushFinishedGood(FinishedGood finishedGood) {
        return finishedGoodRepository.saveAndFlush(finishedGood);
    }

    public FinishedGood saveFinishedGood(FinishedGood finishedGood) {
        return finishedGoodRepository.save(finishedGood);
    }

    public List<FinishedGood> findByName(String name) {
        return finishedGoodRepository.findByName(name);
    }

    public Optional<FinishedGood> findById(Long id) {
        return finishedGoodRepository.findById(id);
    }

    public FinishedGood findFirstById(Long id) {
        return finishedGoodRepository.findFirstById(id);
    }

    public List<FinishedGood> findAll() {
        return finishedGoodRepository.findAll();
    }

}
