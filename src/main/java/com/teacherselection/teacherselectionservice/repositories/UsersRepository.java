package com.teacherselection.teacherselectionservice.repositories;

import com.teacherselection.teacherselectionservice.entities.Users;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository <Users,String>{
    Users findByEmail(String email);
}
