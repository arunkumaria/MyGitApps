package com.example.demo_azure.repo;

import com.example.demo_azure.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
