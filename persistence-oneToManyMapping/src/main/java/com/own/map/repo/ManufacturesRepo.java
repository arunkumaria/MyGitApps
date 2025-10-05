package com.own.map.repo;

import com.own.map.models.Manufactures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ManufacturesRepo extends JpaRepository<Manufactures, Integer> {
}
