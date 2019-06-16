/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.controller;

import com.senbazuru.inventory.model.RawMaterial;
import com.senbazuru.inventory.service.RawMaterialService;
import com.senbazuru.inventory.viewmodel.RawMaterialCreationFormData;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Binh
 */
@Controller
public class RawMaterialAdderController {
    
    @Autowired
    RawMaterialService rawMaterialService;
    
    

    @RequestMapping(value = "/rawmaterialadder", method = RequestMethod.GET)
    public String showRawMaterialAdderPage(Model model) {

        model.addAttribute("RawMaterialCreationFormData", new RawMaterialCreationFormData());
        return "rawmaterialadder.html";
    }
    
        @RequestMapping(value = "/rawmaterialadder", method = RequestMethod.POST)
    public String createRawMaterial(Model model,
            @ModelAttribute("movieCreationFormData") RawMaterialCreationFormData rawMaterialCreationFormData, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            RawMaterial rawMaterial = new RawMaterial();
            

            String name = rawMaterialCreationFormData.getName();
            rawMaterial.setName(name);
            
            int purchasePrice = Integer.parseInt(rawMaterialCreationFormData.getPurchasePrice());
            rawMaterial.setPurchasePrice(BigDecimal.valueOf(purchasePrice));
            
            int totalStock = Integer.parseInt(rawMaterialCreationFormData.getTotalStock());
            rawMaterial.setTotalStock(totalStock);

          
            rawMaterialService.saveRawMaterial(rawMaterial);
            model.addAttribute("successMessage", "Succesful creation!");
        }
        return showRawMaterialAdderPage(model);
    }
}
