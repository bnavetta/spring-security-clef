package com.bennavetta.clef.sample;

import com.bennavetta.clef.boot.form.ClefForm;
import com.bennavetta.clef.boot.form.LoginHelper;
import com.bennavetta.clef.boot.form.Phrasing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthController
{
    @Autowired
    LoginHelper loginHelper;

    @RequestMapping("/login")
    public String loginForm(HttpServletRequest request, Model model)
    {
        ClefForm form = loginHelper.prepareLogin(request, Phrasing.LOGIN);
        model.addAttribute("title", "Log In");
        model.addAttribute("loginForm", form);
        model.addAttribute("isRegistering", false);
        return "login-or-register";
    }

    @RequestMapping("/register")
    public String registerForm(HttpServletRequest request, Model model)
    {
        ClefForm form = loginHelper.prepareLogin(request, Phrasing.REGISTER);
        model.addAttribute("title", "Register");
        model.addAttribute("loginForm", form);
        model.addAttribute("isRegistering", true);
        return "login-or-register";
    }
}
