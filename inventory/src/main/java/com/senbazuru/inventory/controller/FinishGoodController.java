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
public class FinishGoodController {

    @Autowired
    private FinishedGoodService finishedGoodService;

    @Autowired
    FinishedGoodCategoryService finishedGoodCategoryService;

    @Autowired
    RawMaterialService rawMaterialService;

    @RequestMapping("/finishedgood")
    public String index(Model model) {

        List<FinishedGood> finishedGoodList = finishedGoodService.findAll();
        List<ReSaleProduct> reSaleProducts = new ArrayList<>();
        List<CookedProduct> cookedProducts = new ArrayList<>();

        for (FinishedGood finishedGood : finishedGoodList) {
            if (finishedGood instanceof CookedProduct) {
                cookedProducts.add((CookedProduct) finishedGood);
            } else {
                reSaleProducts.add((ReSaleProduct) finishedGood);
            }

        }

        model.addAttribute("finishedGoodList", finishedGoodService.findAll());
        model.addAttribute("reSaleProducts", reSaleProducts);
        model.addAttribute("cookedProducts", cookedProducts);

        return "finishedgood.html";
    }

    @GetMapping("/finishedgoodpage/{productId}")
    public String displayFinishedGood(@PathVariable("productId") Long productId, Model model) {
        FinishedGood finishedGood = finishedGoodService.findFirstById(productId);

        List<FinishedGoodCategory> finishedGoodCategorys = finishedGoodCategoryService.findAll();
        List<RawMaterial> rawMaterials = rawMaterialService.findAll();

        model.addAttribute("finishedGoodCategorys", finishedGoodCategorys);
        model.addAttribute("rawMaterials", rawMaterials);
        model.addAttribute("FinishedGoodCreationFormData", new FinishedGoodCreationFormData());

        if (finishedGood instanceof ReSaleProduct) {
            model.addAttribute("finishedgood", finishedGood);
            return "resaleproduct.html";
        } else if (finishedGood instanceof CookedProduct) {
            model.addAttribute("finishedgood", finishedGood);
            return "cookedproduct.html";
        } else {

            return null;
        }

    }

    @RequestMapping(value = "/finishedgoodpage/change/{productId}", method = RequestMethod.POST)
    public String createFinishedGood(Model model,
            @ModelAttribute("FinishedGoodCreationFormData") FinishedGoodCreationFormData finishedGoodCreationFormData, BindingResult bindingResult, @PathVariable("productId") Long productId) {
        if (!bindingResult.hasErrors()) {
            FinishedGood finishedGood = finishedGoodService.findFirstById(productId);

            if (finishedGoodCreationFormData.getCookable().equals("Yes")) {
                CookedProduct cookedProduct = (CookedProduct) finishedGood;

                String name = finishedGoodCreationFormData.getName();

                if (!name.equals("")) {
                    cookedProduct.setName(name);

                }
                String imageLink = finishedGoodCreationFormData.getImageLink();
                if (!imageLink.equals("")) {
                    cookedProduct.setImageLink(imageLink);
                }

                List<FinishedGoodCategory> categories = processCategories(finishedGoodCreationFormData, cookedProduct);
                cookedProduct.setFinishedGoodCategory(categories);

                if (!finishedGoodCreationFormData.getSellingPrice().equals("")) {
                    int sellingPrice = Integer.parseInt(finishedGoodCreationFormData.getSellingPrice());
                    cookedProduct.setSellingPrice(BigDecimal.valueOf(sellingPrice));
                }

                Map<RawMaterial, Integer> itemQuantityMap = processRawMaterialsAndQuantities(finishedGoodCreationFormData);
                cookedProduct.setItemQuantityMap(itemQuantityMap);
                finishedGoodService.saveFinishedGood(cookedProduct);

            } else {

                ReSaleProduct reSaleProduct = (ReSaleProduct) finishedGood;

                String name = finishedGoodCreationFormData.getName();

                if (!name.equals("")) {
                    reSaleProduct.setName(name);

                }
                String imageLink = finishedGoodCreationFormData.getImageLink();
                if (!imageLink.equals("")) {
                    reSaleProduct.setImageLink(imageLink);
                }

                List<FinishedGoodCategory> categories = processCategories(finishedGoodCreationFormData, reSaleProduct);
                reSaleProduct.setFinishedGoodCategory(categories);

                if (!finishedGoodCreationFormData.getSellingPrice().equals("")) {
                    int sellingPrice = Integer.parseInt(finishedGoodCreationFormData.getSellingPrice());
                    reSaleProduct.setSellingPrice(BigDecimal.valueOf(sellingPrice));
                }

                if (!finishedGoodCreationFormData.getPurchasePrice().equals("")) {
                    int purchasePrice = Integer.parseInt(finishedGoodCreationFormData.getPurchasePrice());
                    reSaleProduct.setPurchasePrice(BigDecimal.valueOf(purchasePrice));
                }

                if (!finishedGoodCreationFormData.getTotalStock().equals("")) {
                    reSaleProduct.setTotalStock(Integer.parseInt(finishedGoodCreationFormData.getTotalStock()));
                }

                if (!finishedGoodCreationFormData.getMinimumStock().equals("")) {
                    reSaleProduct.setMinimumStock(Integer.parseInt(finishedGoodCreationFormData.getMinimumStock()));
                }

                finishedGoodService.saveFinishedGood(reSaleProduct);
            }

            model.addAttribute("successMessage", "Succesful change for product!");

        }
        return index(model);
    }

    private List<FinishedGoodCategory> processCategories(FinishedGoodCreationFormData finishedGoodCreationFormData, FinishedGood finishedGood) {

        List<FinishedGoodCategory> categories = new ArrayList<>();
        FinishedGoodCategory finishedgoodcategory = finishedGoodCategoryService.
                findByCategory(Category.valueOf(finishedGoodCreationFormData.getFinishedGoodCategory())).get(0);
        List<FinishedGood> FinishedGoodListOfTheFirstCategory = finishedgoodcategory.getFinishedGoodList();
        categories.add(finishedgoodcategory);
        return categories;
    }

    private Map<RawMaterial, Integer> processRawMaterialsAndQuantities(FinishedGoodCreationFormData finishedGoodCreationFormData) {
        Map<RawMaterial, Integer> rawMaterialList = new HashMap<>();
        String[] rawMaterialData = {finishedGoodCreationFormData.getRawMaterial1(), finishedGoodCreationFormData.getRawMaterial2(),
            finishedGoodCreationFormData.getRawMaterial3(), finishedGoodCreationFormData.getRawMaterial4(),
            finishedGoodCreationFormData.getRawMaterial5(), finishedGoodCreationFormData.getRawMaterial6(), finishedGoodCreationFormData.getRawMaterial7()};

        String[] quantityData = {finishedGoodCreationFormData.getRawMaterial1quantityneeded(), finishedGoodCreationFormData.getRawMaterial2quantityneeded(),
            finishedGoodCreationFormData.getRawMaterial3quantityneeded(), finishedGoodCreationFormData.getRawMaterial4quantityneeded(),
            finishedGoodCreationFormData.getRawMaterial5quantityneeded(), finishedGoodCreationFormData.getRawMaterial6quantityneeded(), finishedGoodCreationFormData.getRawMaterial7quantityneeded()};

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
