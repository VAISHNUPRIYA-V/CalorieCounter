package com.example.CalorieCalcu.services;

import com.example.CalorieCalcu.models.RegisterDetails;
import com.example.CalorieCalcu.repository.RegisterDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    RegisterDetailsRepository registerDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        RegisterDetails user = registerDetailsRepository.findByUserName(username)
                .orElseThrow(()->new RuntimeException("User Not Found"));
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(roles -> new SimpleGrantedAuthority("ROLE_" + roles.getRoleName()))
                .collect(Collectors.toSet());
        System.out.println("Username is " + user.getUserName()+"\nPassword is " + user.getPassword() + "\nAuthority is " + authorities);
        return new User(user.getUserName(),user.getPassword(),authorities);
    }
}