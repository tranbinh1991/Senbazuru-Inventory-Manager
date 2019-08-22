/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.controller;

import com.senbazuru.inventory.model.FinishedGood;
import com.senbazuru.inventory.service.FinishedGoodService;
import com.senbazuru.inventory.service.TableSessionService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Binh
 */
@Controller
public class TableSessionController {

    private final TableSessionService tableSessionService;

    private final FinishedGoodService productService;

    @Autowired
    public TableSessionController(TableSessionService shoppingCartService, FinishedGoodService productService) {
        this.tableSessionService = shoppingCartService;
        this.productService = productService;
    }

    @GetMapping("/shoppingCart/{tableId}")
    public ModelAndView shoppingCart(@PathVariable("tableId") Long tableId) {

        tableSessionService.setCurrentTable(tableId);

        ModelAndView modelAndView = new ModelAndView("/shoppingCart");
        List<FinishedGood> drinkList = new ArrayList<>();
        List<FinishedGood> sushiList = new ArrayList<>();
        List<FinishedGood> phoList = new ArrayList<>();
        List<FinishedGood> maincourseList = new ArrayList<>();
        List<FinishedGood> ramenList = new ArrayList<>();
        List<FinishedGood> dessertList = new ArrayList<>();
        List<FinishedGood> starterList = new ArrayList<>();

        for (FinishedGood finishedGood : productService.findAll()) {
            if (finishedGood.getFinishedGoodCategory().get(0).getCategory().name().equals("DRINK")) {
                drinkList.add(finishedGood);
            } else if (finishedGood.getFinishedGoodCategory().get(0).getCategory().name().equals("SUSHI")) {
                sushiList.add(finishedGood);
            } else if (finishedGood.getFinishedGoodCategory().get(0).getCategory().name().equals("PHO")) {
                phoList.add(finishedGood);
            } else if (finishedGood.getFinishedGoodCategory().get(0).getCategory().name().equals("MAINCOURSE")) {
                maincourseList.add(finishedGood);
            } else if (finishedGood.getFinishedGoodCategory().get(0).getCategory().name().equals("RAMEN")) {
                ramenList.add(finishedGood);
            } else if (finishedGood.getFinishedGoodCategory().get(0).getCategory().name().equals("DESSERT")) {
                dessertList.add(finishedGood);
            } else if (finishedGood.getFinishedGoodCategory().get(0).getCategory().name().equals("STARTER")) {
                starterList.add(finishedGood);
            }

        }
        
        modelAndView.addObject("drinkList", drinkList);
        modelAndView.addObject("sushiList", sushiList);
        modelAndView.addObject("phoList", phoList);
        modelAndView.addObject("maincourseList", maincourseList);
        modelAndView.addObject("ramenList", ramenList);
        modelAndView.addObject("dessertList", dessertList);
        modelAndView.addObject("starterList", starterList);

        modelAndView.addObject("finishedGoodList", productService.findAll());
        modelAndView.addObject("products", tableSessionService.getProductsInCart());
        modelAndView.addObject("total", tableSessionService.getTotal().toString());
        modelAndView.addObject("tableId", tableId);
        return modelAndView;
    }

    @GetMapping("/shoppingCart/addProduct/{productId}")
    public ModelAndView addProductToCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(tableSessionService::addProduct);

        return shoppingCart(tableSessionService.getCurrentTable());
    }

    @GetMapping("/shoppingCart/removeProduct/{productId}")
    public ModelAndView removeProductFromCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(tableSessionService::removeProduct);

        return shoppingCart(tableSessionService.getCurrentTable());
    }

}
