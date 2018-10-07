package com.teacherselection.teacherselectionservice.repositories;


import com.teacherselection.teacherselectionservice.entities.Role;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByRole(String role);
}