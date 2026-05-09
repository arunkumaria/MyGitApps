package com.own.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.own.domain.model.SwapRequest;

@Repository
public interface SwapRequestRepository extends JpaRepository<SwapRequest,Long>{

    List<SwapRequest> findByReceiverId(Long id);
}