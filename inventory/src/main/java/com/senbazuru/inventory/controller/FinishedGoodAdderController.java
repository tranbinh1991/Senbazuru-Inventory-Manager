/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.controller;

import com.senbazuru.inventory.model.Category;
import com.senbazuru.inventory.model.FinishedGood;
import com.senbazuru.inventory.model.FinishedGoodCategory;
import com.senbazuru.inventory.model.RawMaterial;
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
public class FinishedGoodAdderController {

    @Autowired
    FinishedGoodService finishedGoodService;

    @Autowired
    FinishedGoodCategoryService finishedGoodCategoryService;

    @Autowired
    RawMaterialService rawMaterialService;

    @RequestMapping(value = "/finishedgoodadder", method = RequestMethod.GET)
    public String showFinishedGoodAdderPage(Model model) {
        List<FinishedGoodCategory> finishedGoodCategorys = finishedGoodCategoryService.findAll();
        List<RawMaterial> rawMaterials = rawMaterialService.findAll();

        model.addAttribute("finishedGoodCategorys", finishedGoodCategorys);
        model.addAttribute("rawMaterials", rawMaterials);
        model.addAttribute("FinishedGoodCreationFormData", new FinishedGoodCreationFormData());
        return "finishedgoodadder.html";
    }

    @RequestMapping(value = "/finishedgoodadder", method = RequestMethod.POST)
    public String createFinishedGood(Model model,
            @ModelAttribute("FinishedGoodCreationFormData") FinishedGoodCreationFormData finishedGoodCreationFormData, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            FinishedGood finishedGood = new FinishedGood();

            String name = finishedGoodCreationFormData.getName();
            finishedGood.setName(name);

            List<FinishedGoodCategory> categories = processCategories(finishedGoodCreationFormData, finishedGood);
            finishedGood.setFinishedGoodCategory(categories);

            int sellingPrice = Integer.parseInt(finishedGoodCreationFormData.getSellingPrice());
            finishedGood.setSellingPrice(BigDecimal.valueOf(sellingPrice));



            if (finishedGoodCreationFormData.getCookable().equals("Yes")) {
                finishedGood.setCookable(Boolean.TRUE);
                Map<RawMaterial, Integer> itemQuantityMap = processRawMaterialsAndQuantities(finishedGoodCreationFormData, finishedGood);
                finishedGood.setItemQuantityMap(itemQuantityMap);
                

            } else {
                finishedGood.setCookable(Boolean.FALSE);
                int purchasePrice = Integer.parseInt(finishedGoodCreationFormData.getPurchasePrice());
                finishedGood.setPurchasePrice(BigDecimal.valueOf(purchasePrice));
                finishedGood.setTotalStock(Integer.parseInt(finishedGoodCreationFormData.getTotalStock()));
            }

            finishedGoodService.saveFinishedGood(finishedGood);

            model.addAttribute("successMessage", "Succesful creation!");
        }
        return showFinishedGoodAdderPage(model);
    }

    private List<FinishedGoodCategory> processCategories(FinishedGoodCreationFormData finishedGoodCreationFormData, FinishedGood finishedGood) {

        List<FinishedGoodCategory> categories = new ArrayList<>();
        FinishedGoodCategory finishedgoodcategory = finishedGoodCategoryService.
                findByCategory(Category.valueOf(finishedGoodCreationFormData.getFinishedGoodCategory())).get(0);
        List<FinishedGood> FinishedGoodListOfTheFirstCategory = finishedgoodcategory.getFinishedGoodList();
        categories.add(finishedgoodcategory);
        FinishedGoodListOfTheFirstCategory.add(finishedGood);

        return categories;
    }


    private Map<RawMaterial, Integer> processRawMaterialsAndQuantities(FinishedGoodCreationFormData finishedGoodCreationFormData, FinishedGood finishedGood) {
        Map<RawMaterial, Integer> rawMaterialList = new HashMap<>();
        String[] rawMaterialData = {finishedGoodCreationFormData.getRawMaterial1(), finishedGoodCreationFormData.getRawMaterial2(),
            finishedGoodCreationFormData.getRawMaterial3(), finishedGoodCreationFormData.getRawMaterial4(),
            finishedGoodCreationFormData.getRawMaterial5(), finishedGoodCreationFormData.getRawMaterial6(), finishedGoodCreationFormData.getRawMaterial7()};

        String[] quantityData = {finishedGoodCreationFormData.getRawMaterial1quantityneeded(), finishedGoodCreationFormData.getRawMaterial2quantityneeded(),
            finishedGoodCreationFormData.getRawMaterial3(), finishedGoodCreationFormData.getRawMaterial4quantityneeded(),
            finishedGoodCreationFormData.getRawMaterial5quantityneeded(), finishedGoodCreationFormData.getRawMaterial6(), finishedGoodCreationFormData.getRawMaterial7quantityneeded()};

        for (int i = 0; i < rawMaterialData.length; i++) {
            if (!rawMaterialData[i].equals("")) {
                RawMaterial rawMaterial = rawMaterialService.findByName(rawMaterialData[i]).get(0);
                System.out.println(rawMaterial.getName());

                rawMaterialList.put(rawMaterial, Integer.parseInt(quantityData[i]));
            }
        }


        return rawMaterialList;
    }
}
