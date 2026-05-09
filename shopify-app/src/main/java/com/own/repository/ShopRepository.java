package com.own.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.own.model.Shop;

public interface ShopRepository extends JpaRepository<Shop, String> {
}