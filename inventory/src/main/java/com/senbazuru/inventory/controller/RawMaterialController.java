/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.controller;

import com.senbazuru.inventory.model.Category;
import com.senbazuru.inventory.model.CookedProduct;
import com.senbazuru.inventory.model.FinishedGood;
import com.senbazuru.inventory.model.FinishedGoodCategory;
import com.senbazuru.inventory.model.RawMaterial;
import com.senbazuru.inventory.model.ReSaleProduct;
import com.senbazuru.inventory.service.FinishedGoodCategoryService;
import com.senbazuru.inventory.service.FinishedGoodService;
import com.senbazuru.inventory.service.RawMaterialService;
import com.senbazuru.inventory.viewmodel.FinishedGoodCreationFormData;
import com.senbazuru.inventory.viewmodel.RawMaterialCreationFormData;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Binh
 */
@Controller
public class RawMaterialController {

    @Autowired
    private FinishedGoodService finishedGoodService;

    @Autowired
    FinishedGoodCategoryService finishedGoodCategoryService;

    @Autowired
    RawMaterialService rawMaterialService;

    @RequestMapping("/rawmaterial")
    public String index(Model model) {

        List<RawMaterial> rawMaterials = rawMaterialService.findAll();

        model.addAttribute("rawMaterialList", rawMaterials);

        return "rawmaterial.html";
    }

    @GetMapping("/rawmaterialchange/{productId}")
    public String displayRawMaterial(@PathVariable("productId") Long productId, Model model) {
        RawMaterial rawMaterial = rawMaterialService.findFirstById(productId);

        model.addAttribute("rawmaterial", rawMaterial);
        model.addAttribute("RawMaterialCreationFormData", new RawMaterialCreationFormData());
        return "rawmaterialchange.html";

    }

    @RequestMapping(value = "/rawmaterial/change/{productId}", method = RequestMethod.POST)
    public String createFinishedGood(Model model,
            @ModelAttribute("FinishedGoodCreationFormData") RawMaterialCreationFormData rawMaterialCreationFormData, BindingResult bindingResult, @PathVariable("productId") Long productId) {
        if (!bindingResult.hasErrors()) {
            RawMaterial rawMaterial = rawMaterialService.findFirstById(productId);

            String name = rawMaterialCreationFormData.getName();

            if (!name.equals("")) {
                rawMaterial.setName(name);

            }
            String imageLink = rawMaterialCreationFormData.getImageLink();
            if (!imageLink.equals("")) {
                rawMaterial.setImageLink(imageLink);
            }

            if (!rawMaterialCreationFormData.getPurchasePrice().equals("")) {
                int purchasePrice = Integer.parseInt(rawMaterialCreationFormData.getPurchasePrice());
                rawMaterial.setPurchasePrice(BigDecimal.valueOf(purchasePrice));
            }

            if (!rawMaterialCreationFormData.getTotalStock().equals("")) {
                rawMaterial.setTotalStock(Integer.parseInt(rawMaterialCreationFormData.getTotalStock()));
            }

            if (!rawMaterialCreationFormData.getMinimumStock().equals("")) {
                rawMaterial.setMinimumStock(Integer.parseInt(rawMaterialCreationFormData.getMinimumStock()));
            }

            rawMaterialService.saveRawMaterial(rawMaterial);
        }

        model.addAttribute("successMessage", "Succesful change for product!");

        return index(model);
    }
}
