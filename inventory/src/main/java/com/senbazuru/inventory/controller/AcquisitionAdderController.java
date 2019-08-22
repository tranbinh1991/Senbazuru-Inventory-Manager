/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.controller;

import com.senbazuru.inventory.model.Acquisition;
import com.senbazuru.inventory.model.CookedProduct;
import com.senbazuru.inventory.model.FinishedGood;
import com.senbazuru.inventory.model.FinishedGoodCategory;
import com.senbazuru.inventory.model.RawMaterial;
import com.senbazuru.inventory.model.ReSaleProduct;
import com.senbazuru.inventory.service.AcquisitionService;
import com.senbazuru.inventory.service.FinishedGoodService;
import com.senbazuru.inventory.service.RawMaterialService;
import com.senbazuru.inventory.viewmodel.AcquisitionCreationFormData;
import com.senbazuru.inventory.viewmodel.FinishedGoodCreationFormData;
import com.senbazuru.inventory.viewmodel.RawMaterialCreationFormData;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class AcquisitionAdderController {

    @Autowired
    AcquisitionService acquistionService;

    @Autowired
    RawMaterialService rawMaterialService;

    @Autowired
    FinishedGoodService finishedGoodService;

    @RequestMapping(value = "/acquisitionpage", method = RequestMethod.GET)
    public String showRawMaterialAdderPage(Model model) {

        List<RawMaterial> rawMaterials = rawMaterialService.findAll();
        List<FinishedGood> finishedGoods = finishedGoodService.findAll();
        List<ReSaleProduct> reSaleProducts = new ArrayList<>();

        for (FinishedGood finishedGood : finishedGoods) {
            if (finishedGood instanceof ReSaleProduct) {
                reSaleProducts.add((ReSaleProduct) finishedGood);
            }
        }

        model.addAttribute("reSaleProducts", reSaleProducts);
        model.addAttribute("AcquisitionCreationFormData", new AcquisitionCreationFormData());
        model.addAttribute("rawMaterials", rawMaterials);
        return "acquisitionpage.html";
    }

    @RequestMapping(value = "/acquisitionpage", method = RequestMethod.POST)
    public String createFinishedGood(Model model,
            @ModelAttribute("AcquisitionCreationFormData") AcquisitionCreationFormData aquisitionCreationFormData, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {

            Acquisition acquisition = new Acquisition();
            BigDecimal totalAcquisitionPrice = BigDecimal.ZERO;
            acquisition.setLocalDateTime(LocalDateTime.now());

            Map<ReSaleProduct, Integer> reSaleProductsQuantityMap = processResaleProductsAndQuantities(aquisitionCreationFormData);
            acquisition.setReSaleProductQuantityMap(reSaleProductsQuantityMap);
            Map<RawMaterial, Integer> rawMaterialsQuantityMap = processRawMaterialsAndQuantities(aquisitionCreationFormData);
            acquisition.setRawMaterialQuantityMap(rawMaterialsQuantityMap);

            for (Map.Entry<RawMaterial, Integer> entry : rawMaterialsQuantityMap.entrySet()) {
                RawMaterial key = entry.getKey();
                Integer value = entry.getValue();

                RawMaterial material = key;
                material.setTotalStock(material.getTotalStock() + value);
                totalAcquisitionPrice = totalAcquisitionPrice.add(material.getPurchasePrice().multiply(BigDecimal.valueOf(value)));
                rawMaterialService.saveRawMaterial(material);
            }

            for (Map.Entry<ReSaleProduct, Integer> entry : reSaleProductsQuantityMap.entrySet()) {
                ReSaleProduct key = entry.getKey();
                Integer value = entry.getValue();

                ReSaleProduct reSaleProduct = key;
                reSaleProduct.setTotalStock(reSaleProduct.getTotalStock() + value);
                totalAcquisitionPrice = totalAcquisitionPrice.add(reSaleProduct.getPurchasePrice().multiply(BigDecimal.valueOf(value)));
                finishedGoodService.saveFinishedGood(reSaleProduct);
            }

            acquisition.setTotalPurchasePrice(totalAcquisitionPrice);

            acquistionService.saveAndFlushAcquisition(acquisition);
            model.addAttribute("successMessage", "Succesful creation!");
        }
        return showRawMaterialAdderPage(model);
    }

    private Map<RawMaterial, Integer> processRawMaterialsAndQuantities(AcquisitionCreationFormData aquisitionCreationFormData) {
        Map<RawMaterial, Integer> rawMaterialList = new HashMap<>();
        String[] rawMaterialData = {aquisitionCreationFormData.getRawMaterial1(), aquisitionCreationFormData.getRawMaterial2(),
            aquisitionCreationFormData.getRawMaterial3(), aquisitionCreationFormData.getRawMaterial4(),
            aquisitionCreationFormData.getRawMaterial5(), aquisitionCreationFormData.getRawMaterial6(), aquisitionCreationFormData.getRawMaterial7()};

        String[] quantityData = {aquisitionCreationFormData.getRawMaterial1quantityneeded(), aquisitionCreationFormData.getRawMaterial2quantityneeded(),
            aquisitionCreationFormData.getRawMaterial3quantityneeded(), aquisitionCreationFormData.getRawMaterial4quantityneeded(),
            aquisitionCreationFormData.getRawMaterial5quantityneeded(), aquisitionCreationFormData.getRawMaterial6quantityneeded(), aquisitionCreationFormData.getRawMaterial7quantityneeded()};

        for (int i = 0; i < rawMaterialData.length; i++) {
            if (!rawMaterialData[i].equals("")) {
                RawMaterial rawMaterial = rawMaterialService.findByName(rawMaterialData[i]).get(0);
                System.out.println(rawMaterial.getName());

                rawMaterialList.put(rawMaterial, Integer.parseInt(quantityData[i]));
            }
        }

        return rawMaterialList;
    }

    private Map<ReSaleProduct, Integer> processResaleProductsAndQuantities(AcquisitionCreationFormData aquisitionCreationFormData) {
        Map<ReSaleProduct, Integer> ReSaleProductlList = new HashMap<>();
        String[] ReSaleproductData = {aquisitionCreationFormData.getReSaleProduct1(), aquisitionCreationFormData.getReSaleProduct2(),
            aquisitionCreationFormData.getReSaleProduct3(), aquisitionCreationFormData.getReSaleProduct4(),
            aquisitionCreationFormData.getReSaleProduct5(), aquisitionCreationFormData.getReSaleProduct6(), aquisitionCreationFormData.getReSaleProduct7()};

        String[] quantityData = {aquisitionCreationFormData.getReSaleProduct1quantityneeded(), aquisitionCreationFormData.getReSaleProduct2quantityneeded(),
            aquisitionCreationFormData.getReSaleProduct3quantityneeded(), aquisitionCreationFormData.getReSaleProduct4quantityneeded(),
            aquisitionCreationFormData.getReSaleProduct5quantityneeded(), aquisitionCreationFormData.getReSaleProduct6quantityneeded(), aquisitionCreationFormData.getReSaleProduct7quantityneeded()};

        for (int i = 0; i < ReSaleproductData.length; i++) {
            if (!ReSaleproductData[i].equals("")) {
                ReSaleProduct reSaleProduct = (ReSaleProduct) finishedGoodService.findByName(ReSaleproductData[i]).get(0);
                System.out.println(reSaleProduct.getName());

                ReSaleProductlList.put(reSaleProduct, Integer.parseInt(quantityData[i]));
            }
        }

        return ReSaleProductlList;
    }
}
