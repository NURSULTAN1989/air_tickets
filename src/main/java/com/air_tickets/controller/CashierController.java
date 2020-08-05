package com.air_tickets.controller;

import com.air_tickets.entities.Users;
import com.air_tickets.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/cashier")
@PreAuthorize("hasAnyRole('ROLE_CASHIER')")
public class CashierController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/index")
    public String cashierBookingPage(Model model){
        model.addAttribute("user", getUserData());
        return "cashier/booking";
    }
    @GetMapping(value = "/tickets")
    public String cashierTicketsPage(Model model){
        model.addAttribute("user", getUserData() );
        return "cashier/tickets";
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
