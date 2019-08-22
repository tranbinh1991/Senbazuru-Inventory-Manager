/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.controller;

import com.senbazuru.inventory.model.FinishedGood;
import com.senbazuru.inventory.service.FinishedGoodCategoryService;
import com.senbazuru.inventory.service.FinishedGoodService;
import com.senbazuru.inventory.service.TableSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Binh
 */
@Controller
public class TableController {

    @Autowired
    TableSessionService tableSessionService;

    @GetMapping("/tables")
    public ModelAndView index() {


        ModelAndView modelAndView = new ModelAndView("/tables");
        return modelAndView;
    }

}
