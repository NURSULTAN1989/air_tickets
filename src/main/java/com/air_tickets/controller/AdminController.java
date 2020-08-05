package com.air_tickets.controller;

import com.air_tickets.entities.Aircrafts;
import com.air_tickets.entities.Roles;
import com.air_tickets.entities.Users;
import com.air_tickets.repositories.AircraftRepository;
import com.air_tickets.repositories.RoleRepository;
import com.air_tickets.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class AdminController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AircraftRepository aircraftRepository;
    @GetMapping(value = "/index")
    public String adminMainPage(Model model){
        model.addAttribute("user", getUserData() );
        return "admin/index";
    }
    @GetMapping(value = "/cashier")
    public String adminCashierPage(Model model){
        model.addAttribute("user", getUserData() );
        List<Users> cashier=new ArrayList<>();
        List<Users> users=userRepository.findAll();
        for (Users u:users) {
            for (Roles r: u.getRoles()){
                if (r.getRole().equals("ROLE_CASHIER")){
                    cashier.add(u);
                }
            }
        }
        model.addAttribute("cashier",cashier);
        return "admin/cashier";
    }
    @GetMapping(value = "/aircraft")
    public String aircrafts(Model model){
        ArrayList<Aircrafts> aircrafts=aircraftRepository.findAll();
        model.addAttribute("aircrafts", aircrafts );
        return "admin/aircrafts";
    }
    @PostMapping(value = "/toregister")
    public String registerCashier(Model model, @RequestParam(name="fullname") String fullname,
                                  @RequestParam(name = "login") String login,
                                  @RequestParam(name = "password") String password,
                                  @RequestParam(name = "re_password") String re_password){
        String redirect="redirect:/admin/cashier";
        if (fullname!=null && login!=null && password!=null && re_password!=null && !fullname.trim().equals("")&&
                !login.trim().equals("") && !password.trim().equals("") && !re_password.trim().equals("")){
            if (userRepository.findByLogin(login)==null){
                if (password.equals(re_password)){
                    Roles r=roleRepository.findByRole("ROLE_CASHIER");
                    ArrayList<Roles> role=new ArrayList<>();
                    role.add(r);
                    Users u=new Users(null,login,passwordEncoder.encode(password),fullname,role);
                    userRepository.save(u);
                    redirect=redirect+"?success";
                } else redirect=redirect+"?error_password";
            }else redirect=redirect+"?error_login";
        }else redirect=redirect+"?error_notfilled";
        return redirect;
    }
    @PostMapping(value = "/addaircrafts")
    public String addaircrafts(Model model, @RequestParam(name = "name") String name,
                               @RequestParam(name = "model") String air_model,
                               @RequestParam(name = "econom_capacity") int ec_capacity,
                               @RequestParam(name="business_capacity") int bus_capacity){
        String redirect="redirect:/admin/aircrafts";
        if (!name.trim().equals("")&&name!=null &&
                !air_model.trim().equals("")&&air_model!=null){
            aircraftRepository.save(new Aircrafts(null,name,air_model,ec_capacity,bus_capacity));
            redirect=redirect+"?success";
        } else redirect=redirect+"?error";


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
