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
import com.senbazuru.inventory.model.Scrap;
import com.senbazuru.inventory.service.AcquisitionService;
import com.senbazuru.inventory.service.FinishedGoodService;
import com.senbazuru.inventory.service.MailSendingService;
import com.senbazuru.inventory.service.RawMaterialService;
import com.senbazuru.inventory.service.ScrapService;
import com.senbazuru.inventory.viewmodel.AcquisitionCreationFormData;
import com.senbazuru.inventory.viewmodel.FinishedGoodCreationFormData;
import com.senbazuru.inventory.viewmodel.RawMaterialCreationFormData;
import com.senbazuru.inventory.viewmodel.ScrapCreationFormData;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
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
public class ScrapAdderController {

    @Autowired
    RawMaterialService rawMaterialService;

    @Autowired
    FinishedGoodService finishedGoodService;

    @Autowired
    MailSendingService mailSendingService;

    @Autowired
    ScrapService scrapService;

    @RequestMapping(value = "/scrappage", method = RequestMethod.GET)
    public String showScrapAdderPage(Model model) {

        List<RawMaterial> rawMaterials = rawMaterialService.findAll();
        List<FinishedGood> finishedGoods = finishedGoodService.findAll();

        model.addAttribute("finishedGoods", finishedGoods);
        model.addAttribute("ScrapCreationFormData", new ScrapCreationFormData());
        model.addAttribute("rawMaterials", rawMaterials);
        return "scrappage.html";
    }

    @RequestMapping(value = "/scrappage", method = RequestMethod.POST)
    public String createFinishedGood(Model model,
            @ModelAttribute("ScrapCreationFormData") ScrapCreationFormData scrapCreationFormData, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {

            Scrap scrap = new Scrap();
            scrap.setReason(scrapCreationFormData.getReason());

            if (!scrapCreationFormData.getSellingPrice().equals("")) {
                scrap.setSellingPrice(BigDecimal.valueOf(Double.parseDouble(scrapCreationFormData.getSellingPrice())));

            }

            BigDecimal totalCost = BigDecimal.ZERO;
            scrap.setLocalDateTime(LocalDateTime.now());

            Map<FinishedGood, Integer> finishedGoodsQuantityMap = processFinishedGoodsAndQuantities(scrapCreationFormData);
            scrap.setFinishedGoodQuantityMap(finishedGoodsQuantityMap);
            Map<RawMaterial, Integer> rawMaterialsQuantityMap = processRawMaterialsAndQuantities(scrapCreationFormData);
            scrap.setRawMaterialQuantityMap(rawMaterialsQuantityMap);

            for (Map.Entry<RawMaterial, Integer> entry : rawMaterialsQuantityMap.entrySet()) {
                RawMaterial key = entry.getKey();
                Integer value = entry.getValue();

                RawMaterial material = key;
                material.setTotalStock(material.getTotalStock() - value);
                totalCost = totalCost.add(material.getPurchasePrice().multiply(BigDecimal.valueOf(value)));

                if (material.getTotalStock() <= material.getMinimumStock()) {
                    try {
                        mailSendingService.sendMail(material.getName(), material.getTotalStock());
                    } catch (MessagingException ex) {
                        Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

            for (Map.Entry<FinishedGood, Integer> entry : finishedGoodsQuantityMap.entrySet()) {
                FinishedGood key = entry.getKey();
                Integer value = entry.getValue();

                if (key instanceof ReSaleProduct) {
                    ReSaleProduct reSaleProduct = (ReSaleProduct) key;
                    reSaleProduct.setTotalStock(reSaleProduct.getTotalStock() - value);
                    totalCost = totalCost.add(reSaleProduct.getPurchasePrice().multiply(BigDecimal.valueOf(value)));

                    if (reSaleProduct.getTotalStock() <= reSaleProduct.getMinimumStock()) {
                        try {
                            mailSendingService.sendMail(reSaleProduct.getName(), reSaleProduct.getTotalStock());
                        } catch (MessagingException ex) {
                            Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                } else if (key instanceof CookedProduct) {

                    CookedProduct cookedProduct = (CookedProduct) key;
                    for (Map.Entry<RawMaterial, Integer> entry1 : cookedProduct.getItemQuantityMap().entrySet()) {
                        RawMaterial key1 = entry1.getKey();
                        Integer value1 = entry1.getValue();
                        key1.setTotalStock(key1.getTotalStock() - (value * value1));
                        totalCost = totalCost.add(key1.getPurchasePrice().multiply(BigDecimal.valueOf(value1).multiply(BigDecimal.valueOf(value))));

                        if (key1.getTotalStock() <= key1.getMinimumStock()) {
                            try {
                                mailSendingService.sendMail(key1.getName(), key1.getTotalStock());
                            } catch (MessagingException ex) {
                                Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                } else {
                    System.out.println("nem muxik");
                }

                model.addAttribute("successMessage", "Succesful creation!");
            }
            scrap.setTotalCost(totalCost);

            scrapService.saveAndFlushAcquisition(scrap);

        }
        return showScrapAdderPage(model);
    }

    private Map<RawMaterial, Integer> processRawMaterialsAndQuantities(ScrapCreationFormData scrapCreationFormData) {
        Map<RawMaterial, Integer> rawMaterialList = new HashMap<>();
        String[] rawMaterialData = {scrapCreationFormData.getRawMaterial1(), scrapCreationFormData.getRawMaterial2(),
            scrapCreationFormData.getRawMaterial3(), scrapCreationFormData.getRawMaterial4(),
            scrapCreationFormData.getRawMaterial5(), scrapCreationFormData.getRawMaterial6(), scrapCreationFormData.getRawMaterial7()};

        String[] quantityData = {scrapCreationFormData.getRawMaterial1quantityneeded(), scrapCreationFormData.getRawMaterial2quantityneeded(),
            scrapCreationFormData.getRawMaterial3quantityneeded(), scrapCreationFormData.getRawMaterial4quantityneeded(),
            scrapCreationFormData.getRawMaterial5quantityneeded(), scrapCreationFormData.getRawMaterial6quantityneeded(), scrapCreationFormData.getRawMaterial7quantityneeded()};

        for (int i = 0; i < rawMaterialData.length; i++) {
            if (!rawMaterialData[i].equals("")) {
                RawMaterial rawMaterial = rawMaterialService.findByName(rawMaterialData[i]).get(0);

                rawMaterialList.put(rawMaterial, Integer.parseInt(quantityData[i]));
            }
        }

        return rawMaterialList;
    }

    private Map<FinishedGood, Integer> processFinishedGoodsAndQuantities(ScrapCreationFormData scrapCreationFormData) {
        Map<FinishedGood, Integer> finishedGoodlList = new HashMap<>();
        String[] finishedGoodData = {scrapCreationFormData.getFinishedGood1(), scrapCreationFormData.getFinishedGood2(),
            scrapCreationFormData.getFinishedGood3(), scrapCreationFormData.getFinishedGood4(),
            scrapCreationFormData.getFinishedGood5(), scrapCreationFormData.getFinishedGood6(), scrapCreationFormData.getFinishedGood7()};

        String[] quantityData = {scrapCreationFormData.getFinishedGood1quantityneeded(), scrapCreationFormData.getFinishedGood2quantityneeded(),
            scrapCreationFormData.getFinishedGood3quantityneeded(), scrapCreationFormData.getFinishedGood4quantityneeded(),
            scrapCreationFormData.getFinishedGood5quantityneeded(), scrapCreationFormData.getFinishedGood6quantityneeded(), scrapCreationFormData.getFinishedGood7quantityneeded()};

        for (int i = 0; i < finishedGoodData.length; i++) {
            if (!finishedGoodData[i].equals("")) {
                FinishedGood finishedGood = finishedGoodService.findByName(finishedGoodData[i]).get(0);

                finishedGoodlList.put(finishedGood, Integer.parseInt(quantityData[i]));
            }
        }

        return finishedGoodlList;
    }

    @RequestMapping(value = "/displayscrap", method = RequestMethod.GET)
    public String displayScrap(Model model) {


        model.addAttribute("scrapList", scrapService.findAll());

        return "displayscrap.html";
    }
}
