package com.example.bankmanapp.Repository;

import com.example.bankmanapp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {




}