package com.own.map.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.own.map.models.Model;


public interface ModelRepo extends JpaRepository<Model, Integer> {
}
