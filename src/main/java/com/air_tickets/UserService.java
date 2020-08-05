package com.air_tickets;

import com.air_tickets.entities.Users;
import com.air_tickets.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users users=userRepository.findByLogin(s);
        if(users!=null){
            User securityUser=new User(users.getLogin(),users.getPassword(),users.getRoles());
            return securityUser;
        } else return null;
    }
}
