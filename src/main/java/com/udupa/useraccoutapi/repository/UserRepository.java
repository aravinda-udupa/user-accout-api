package com.udupa.useraccoutapi.repository;


import com.udupa.useraccoutapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
