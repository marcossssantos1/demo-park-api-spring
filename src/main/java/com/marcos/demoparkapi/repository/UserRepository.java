package com.marcos.demoparkapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcos.demoparkapi.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
