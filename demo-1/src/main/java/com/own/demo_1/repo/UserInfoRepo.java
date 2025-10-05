package com.own.demo_1.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.own.demo_1.entity.UserInfo;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Integer>{
	Optional<UserInfo> findByName(String name);

}
