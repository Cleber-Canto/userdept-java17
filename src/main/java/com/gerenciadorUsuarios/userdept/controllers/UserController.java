package com.gerenciadorUsuarios.userdept.controllers;

import java.util.List;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.gerenciadorUsuarios.userdept.entities.User;
import com.gerenciadorUsuarios.userdept.repositories.UserRepository;

@SuppressWarnings("unused")
@RestController
@RequestMapping(value = "/users")
public class UserController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository repository;
    
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        try {
            List<User> users = repository.findAll();
            logger.info("findAll executed successfully.");
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching users", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    public <X> ResponseEntity<User> findById(@PathVariable Long id) {
        try {
            User user = repository.findById(id).orElseThrow();
            logger.info("User found with id: {}", id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            logger.error("Error finding user with id: {}", id, e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User user) {
        try {
            User newUser = repository.save(user);
            logger.info("User created successfully with id: {}", newUser.getId());
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating user", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
    try {
        if (!repository.existsById(id)) {
            logger.error("User not found with id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id: " + id);
        }
        repository.deleteById(id);
        logger.info("User deleted successfully with id: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully with id: " + id);
    } catch (Exception e) {
        logger.error("Error deleting user with id: {}", id, e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    @PatchMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            User user = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            user.setName(userDetails.getName() != null ? userDetails.getName() : user.getName());
            user.setEmail(userDetails.getEmail() != null ? userDetails.getEmail() : user.getEmail());
            user.setPhone(userDetails.getPhone() != null ? userDetails.getPhone() : user.getPhone());
            user.setPassword(userDetails.getPassword() != null ? userDetails.getPassword() : user.getPassword());
            User updatedUser = repository.save(user);
            logger.info("User updated successfully with id: {}", updatedUser.getId());
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            logger.error("Error updating user with id: {}", id, e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error updating user with id: {}", id, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
