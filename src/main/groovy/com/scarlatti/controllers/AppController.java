package com.scarlatti.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Monday, 10/30/2017
 */
@Controller
public class AppController {

    /**
     * Easier to test this method declaration style,
     * since the Model is easily mocked and
     * the view returned is easily validated.
     *
     * @param m the model to be sent to the template
     * @return the view for this endpoint.
     */
    @GetMapping("/")
    public String index(Model m) {
        return "index";
    }

    /**
     * Fill in the model from information in the request params.
     *
     * @param name name from request params
     * @param m model to be sent to the template
     * @return the view for this endpoint
     */
    @GetMapping("/api/greeting")
    public String api(@RequestParam(value="name", defaultValue="World") String name, Model m) {
        m.addAttribute("name", name);
        return "api";
    }
}
