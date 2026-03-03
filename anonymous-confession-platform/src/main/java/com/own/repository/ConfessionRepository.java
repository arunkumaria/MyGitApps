package com.own.repository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.own.entity.Confession;
import com.own.entity.ConfessionStatus;

public interface ConfessionRepository extends JpaRepository<Confession, UUID> {

    List<Confession> findByStatus(ConfessionStatus status);
}
