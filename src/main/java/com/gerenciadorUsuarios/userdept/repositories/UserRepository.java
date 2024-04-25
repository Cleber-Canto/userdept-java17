package com.gerenciadorUsuarios.userdept.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gerenciadorUsuarios.userdept.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    
} 
