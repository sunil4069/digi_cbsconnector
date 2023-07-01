package com.digi.app.controller;

import com.digi.app.config.JspPathConfig;
import com.digi.app.util.PageTitles;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;


@Controller
public class HomeController {
    private static final String PAGE_TITLE = "pagetitle";

    @GetMapping(value = "/")
    public ModelAndView index(Principal principal) {
        if (principal != null) {
            return dashboard();
        } else {
            return login(null, null);
        }
    }

    //set url value to enter login page
    @GetMapping(value = "/login-page")
    public ModelAndView login(String error, String logout) {

        //calling jsp file
        ModelAndView model = new ModelAndView(JspPathConfig.INDEX);
        //message to display on login attempt
        String msg = "";
        if (!StringUtils.isNullOrEmpty(error)) {
            msg = "Invalid Credentials!";
        } else if (!StringUtils.isNullOrEmpty(logout)) {
            msg = "Logout Successful!";
        } else {
            msg = "Please Login!";
        }
        model.addObject("msg", msg);
        return model;
    }

    @GetMapping(value = "/dashboard")
    public ModelAndView dashboard() {
        ModelAndView model = new ModelAndView(JspPathConfig.DASHBOARD);
        model.addObject(PAGE_TITLE, PageTitles.DASHBOARD);
        return model;
    }

    @GetMapping(value = "/access/denied")
    public ModelAndView getAccessDeniedPage(){
        ModelAndView model = new ModelAndView(JspPathConfig.FORBIDDEN);
        return model;
    }
}
