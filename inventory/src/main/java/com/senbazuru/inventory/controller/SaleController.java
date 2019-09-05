
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.controller;

import com.senbazuru.inventory.model.CookedProduct;
import com.senbazuru.inventory.model.FinishedGood;
import com.senbazuru.inventory.model.RawMaterial;
import com.senbazuru.inventory.model.ReSaleProduct;
import com.senbazuru.inventory.model.Sale;
import com.senbazuru.inventory.service.FinishedGoodService;
import com.senbazuru.inventory.service.MailSendingService;
import com.senbazuru.inventory.service.RawMaterialService;
import com.senbazuru.inventory.service.SaleService;
import com.senbazuru.inventory.service.TableSessionService;
import com.senbazuru.inventory.viewmodel.DiscountCreationFormData;
import com.senbazuru.inventory.viewmodel.RawMaterialCreationFormData;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
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
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private TableSessionService tableSessionService;

    @Autowired
    private FinishedGoodService finishedGoodService;

    @Autowired
    private RawMaterialService materialService;

    @Autowired
    private MailSendingService mailSendingService;

    @RequestMapping(value = "/sale", params = "succesfulSale", method = RequestMethod.POST)

    public String succesfulSale(Model model,
            @ModelAttribute("DiscountCreationFormData") DiscountCreationFormData discountCreationFormData, BindingResult bindingResult) {
        Sale sale = new Sale();
        Map<FinishedGood, Integer> allProducts = new HashMap();
        allProducts = tableSessionService.getProductsInCart();
        BigDecimal finalPrice = tableSessionService.getTotal();

        if (!discountCreationFormData.getPercentage().equals("")) {
            double discount = 1 - Double.parseDouble(discountCreationFormData.getPercentage()) / 100;
            finalPrice = finalPrice.multiply(BigDecimal.valueOf(discount));
            sale.setDiscount(discount);
            
        }

        sale.setItemQuantityMap(allProducts);
        sale.setLocalDateTime(LocalDateTime.now());
        sale.setTotalSellingPrice(finalPrice);

        BigDecimal totalCost = BigDecimal.ZERO;

        for (Map.Entry<FinishedGood, Integer> entry : allProducts.entrySet()) {
            FinishedGood key = entry.getKey();
            Integer value = entry.getValue();

            if (key instanceof ReSaleProduct) {
                ReSaleProduct reSaleProduct = (ReSaleProduct) key;
                reSaleProduct.setTotalStock(reSaleProduct.getTotalStock() - value);
                finishedGoodService.saveFinishedGood(reSaleProduct);
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
                    materialService.saveRawMaterial(key1);
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

        }
        sale.setTotalCost(totalCost);

        saleService.saveSale(sale);

        clearProductsinCart();

        return "tables.html";
    }

    @RequestMapping(value = "/sale", params = "recipe", method = RequestMethod.POST)
    public String printRecipe(Model model,
            @ModelAttribute("DiscountCreationFormData") DiscountCreationFormData discountCreationFormData, BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {

            BigDecimal finalPrice = tableSessionService.getTotal();

            if (!discountCreationFormData.getPercentage().equals("")) {
                double discount = 1 - Double.parseDouble(discountCreationFormData.getPercentage()) / 100;
                finalPrice = finalPrice.multiply(BigDecimal.valueOf(discount));
                model.addAttribute("discount", Integer.parseInt(discountCreationFormData.getPercentage()) + " % Discount");
            }

            model.addAttribute("sale", tableSessionService.getProductsInCart());
            model.addAttribute("salePrice", finalPrice);

        }

        return "recipe.html";
    }

    @RequestMapping("/displaysales")
    public String displaySales(Model model) {

        model.addAttribute("saleList", saleService.findAll());
        return "displaysales.html";
    }

    public void clearProductsinCart() {
        try {
            tableSessionService.getProductsInCart().clear();
        } catch (Exception e) {

        }
    }

}
