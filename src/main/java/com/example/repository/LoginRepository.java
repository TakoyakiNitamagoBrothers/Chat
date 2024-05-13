package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Users;

@Repository
public interface LoginRepository extends JpaRepository<Users, String> {

    Users findBynameAndPassword(String name, String password);

}