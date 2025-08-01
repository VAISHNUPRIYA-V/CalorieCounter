package com.example.CalorieCalcu.services;


import com.example.CalorieCalcu.jwt.JwtTokenProvider;
import com.example.CalorieCalcu.models.JwtResponse;
import com.example.CalorieCalcu.models.RegisterDetails;
import com.example.CalorieCalcu.models.Roles;
import com.example.CalorieCalcu.models.UserDetailsDto;
import com.example.CalorieCalcu.repository.RegisterDetailsRepository;
import com.example.CalorieCalcu.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class AuthService {

    @Autowired
    RegisterDetailsRepository registerDetailsRepository;

    @Autowired
    RolesRepository rolesRepository;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public String AddNewUser(UserDetailsDto register) {
            RegisterDetails registerDetails=new RegisterDetails();

            registerDetails.setUserId(register.getUserId());
            registerDetails.setName(register.getName());
            registerDetails.setEmail(register.getEmail());
            registerDetails.setPassword(passwordEncoder.encode(register.getPassword()));
            registerDetails.setUserName(register.getUserName());
            Set<Roles> roles = new HashSet<>();
            for(String roleName: register.getRoleNames()){
                Roles role = rolesRepository.findByRoleName(roleName)
                        .orElseThrow(()->new RuntimeException("Role not found" + roleName));
                roles.add(role);
            }
            registerDetails.setRoles(roles);
            registerDetailsRepository.save(registerDetails);
            return "Employee Registered Successfully";
        }
    public JwtResponse authenticate(RegisterDetails login) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUserName(), login.getPassword()));

        String token = jwtTokenProvider.generateToken(authentication);
        RegisterDetails user = registerDetailsRepository.findByUserName(login.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Set<String> roleNames = user.getRoles()
                .stream()
                .map(role -> role.getRoleName())
                .collect(Collectors.toSet());

        return new JwtResponse(token, user.getUserName(), roleNames);
    }

    public List<RegisterDetails> getDetails() {
        return registerDetailsRepository.findAll();
    }
}
