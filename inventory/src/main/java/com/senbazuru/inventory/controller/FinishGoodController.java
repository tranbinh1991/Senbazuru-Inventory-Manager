/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.controller;

import com.senbazuru.inventory.model.FinishedGood;
import com.senbazuru.inventory.service.FinishedGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Binh
 */
@Controller
public class FinishGoodController {
    
    @Autowired
    private FinishedGoodService finishedGoodService;

    @RequestMapping("/finishedgood")
    public String index(Model model) {
        model.addAttribute("finishedGoodList", finishedGoodService.findAll());
        
        return "finishedgood.html";
    }
}

