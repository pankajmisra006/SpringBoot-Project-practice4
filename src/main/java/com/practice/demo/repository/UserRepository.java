package com.practice.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.demo.modal.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByusername(String username);
}
