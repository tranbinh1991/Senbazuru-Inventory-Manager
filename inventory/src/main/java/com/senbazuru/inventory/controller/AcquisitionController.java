/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.controller;

import com.senbazuru.inventory.model.CookedProduct;
import com.senbazuru.inventory.model.FinishedGood;
import com.senbazuru.inventory.model.ReSaleProduct;
import com.senbazuru.inventory.service.AcquisitionService;
import com.senbazuru.inventory.service.FinishedGoodService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Binh
 */
@Controller
public class AcquisitionController {
    
    @Autowired
    private AcquisitionService acquisitionService;

    @RequestMapping("/displayacquisition")
    public String displayAcquisition(Model model) {
        
   
        model.addAttribute("acquisitionList", acquisitionService.findAll());

        
        return "displayacquisition.html";
    }
}

