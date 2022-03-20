package com.program.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.program.practice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
