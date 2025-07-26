package com.example.CalorieCalcu.repository;

import com.example.CalorieCalcu.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles,Integer> {

    Optional<Roles> findByRoleName(String roleName);
}
