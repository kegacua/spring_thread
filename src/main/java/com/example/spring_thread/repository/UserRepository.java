package com.example.spring_thread.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring_thread.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {

}
