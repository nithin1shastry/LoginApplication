package com.hfn.SpringOAuthApp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal OAuth2User principal) {

        if (principal != null) {
            String name = principal.getAttribute("name");
            model.addAttribute("name", name);
            model.addAttribute("email", principal.getAttribute("email"));
            model.addAttribute("picture", principal.getAttribute("picture"));
        }

        return "profile";
    }

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            String name = principal.getAttribute("name");
            model.addAttribute("name", name);
            return "home";
        }

        return "redirect:/home.html";
    }


}
