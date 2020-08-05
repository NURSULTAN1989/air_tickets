package com.air_tickets.controller;

import com.air_tickets.entities.Roles;
import com.air_tickets.entities.Users;
import com.air_tickets.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping(value = "/")
    public String mainPage(){
        return "login";
    }

    @GetMapping(value = "/welcome")
    public  String loginpage(Model model){
        String redirect="redirect:/";
        model.addAttribute("user", getUserData());

        for (Roles r: getUserData().getRoles()) {
            if (r.getAuthority().equals("ROLE_ADMIN")){ redirect="redirect:/admin/index";}
            else if(r.getAuthority().equals("ROLE_CASHIER")){redirect="redirect:/cashier/index";}
        }

        return redirect;
    }
    private Users getUserData(){
        Users users=null;
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            User secUser=(User)authentication.getPrincipal();
            users=userRepository.findByLogin(secUser.getUsername());
        }
        return users;
    }
}
