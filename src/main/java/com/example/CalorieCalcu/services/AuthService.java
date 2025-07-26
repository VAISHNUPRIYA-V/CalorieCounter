package com.example.CalorieCalcu.services;


import com.example.CalorieCalcu.models.RegisterDetails;
import com.example.CalorieCalcu.models.Roles;
import com.example.CalorieCalcu.models.UserDetailsDto;
import com.example.CalorieCalcu.repository.RegisterDetailsRepository;
import com.example.CalorieCalcu.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Service
public class AuthService {

    @Autowired
    RegisterDetailsRepository registerDetailsRepository;

    @Autowired
    RolesRepository rolesRepository;



    public String AddNewUser(UserDetailsDto register) {
        RegisterDetails registerDetails = new RegisterDetails();
        registerDetails.setUserId(register.getUserId());
        registerDetails.setName(register.getName());
        registerDetails.setEmail(register.getEmail());
        registerDetails.setPassword(register.getPassword());
        registerDetails.setUserName(register.getUserName());
        Set<Roles> roles = new HashSet<>();
        for (String roleName : register.getRoleNames()) {
            Roles role = rolesRepository.findByRoleName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found" + roleName));
            roles.add(role);
        }
        registerDetails.setRoles(roles);
        registerDetailsRepository.save(registerDetails);
        return "Employee registered successfully!!!";
    }

    public String authenticate(RegisterDetails login){
        RegisterDetails user=registerDetailsRepository.findByUserName(login.getUserName()).orElse(new RegisterDetails() );
        if (Objects.equals(user.getPassword(), login.getPassword())) {
            return "Login successful";
        } else {
            return "Login not successful";
        }

    }

    public List<RegisterDetails> getDetails() {
        return registerDetailsRepository.findAll();
    }
}
