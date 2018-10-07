package com.teacherselection.teacherselectionservice.controllers;

import com.teacherselection.teacherselectionservice.repositories.UsersRepository;
import com.teacherselection.teacherselectionservice.services.UserService;

import java.util.List;

import com.teacherselection.teacherselectionservice.entities.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class UserController{
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserService userService;

    @RequestMapping(value="/api/admin/users", method=RequestMethod.GET)
    public List<Users> getUsers() {
        return usersRepository.findAll();
    }

    @RequestMapping(value="/api/user", method=RequestMethod.GET)
    public Users findByName(@RequestParam("email") String email) {
        return usersRepository.findByEmail(email);
    }
    
    @RequestMapping(value="/api/admin/user", method=RequestMethod.POST)
    public ResponseEntity<String> newAdminUser(@RequestBody Users user, @RequestParam("role") String role) {
        try {
            userService.saveUser(user,role);
            return ResponseEntity.status(HttpStatus.CREATED).body("The user has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The user already exists or data is missing");
        }
    }

    @RequestMapping(value="/api/user", method=RequestMethod.POST)
    public ResponseEntity<String> newUser(@RequestBody Users user) {
        try {
            userService.saveUser(user,"USER");
            return ResponseEntity.status(HttpStatus.CREATED).body("The user has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The user already exists or data is missing");
        }
    }
    
    @RequestMapping(value="/api/user", method=RequestMethod.PUT)
    public ResponseEntity<String> updateUser(@RequestBody Users user, @RequestParam("email") String email) {
        try {
            Users oldUser = usersRepository.findByEmail(email);
            user.setId(oldUser.getId());
            try {
                usersRepository.save(user);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("The user has been updated");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("An error has ocurred contact with API service");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user doesn't exist");
        }  
    }

    @RequestMapping(value="/api/admin/user", method=RequestMethod.PUT)
    public ResponseEntity<String> updateAdminUser(@RequestBody Users user, @RequestParam("email") String email, @RequestParam("role") String role) {
        try {
            Users oldUser = usersRepository.findByEmail(email);
            user.setId(oldUser.getId());
            try {
                userService.saveUser(user, role);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("The user has been updated");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("An error has ocurred contact with API service");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user doesn't exist");
        }  
    }

    @RequestMapping(value="/api/user", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser( @RequestParam("email") String email) {
        try {
            usersRepository.delete(usersRepository.findByEmail(email));
            return ResponseEntity.status(HttpStatus.OK).body("The user has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user doesn't exist");
        }
    }
    
}