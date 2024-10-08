package com.example.auth_service.repositories;

import com.example.auth_service.models.RolesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RolesInfo, Long> {
    Optional<RolesInfo> findByName(String name);
}
