package com.bennavetta.clef.example;

import com.bennavetta.clef.security.login.LoginHelper;
import com.bennavetta.clef.security.login.LoginState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController
{
    @Autowired
    LoginHelper loginHelper;

    @RequestMapping("/login")
    public String loginForm(HttpServletRequest request, Model model)
    {
        LoginState loginState = loginHelper.prepareForLogin(request);
        model.addAttribute("title", "Log In");
        model.addAttribute("loginState", loginState);
        model.addAttribute("isRegistering", false);
        return "login-or-register";
    }

    @RequestMapping("/register")
    public String registerForm(HttpServletRequest request, Model model)
    {
        LoginState loginState = loginHelper.prepareForLogin(request);
        model.addAttribute("title", "Register");
        model.addAttribute("loginState", loginState);
        model.addAttribute("isRegistering", true);
        return "login-or-register";
    }
}
