package com.xa.posmasterfe.transaction.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/transaction")
public class TransactionController {
    
    @GetMapping("")
    public ModelAndView transactionView() {
        ModelAndView view = new ModelAndView("transaction/index");
        view.addObject("title", "Transaction Page");
        return view;
    }
    
}
