package com.own.domain.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.own.domain.model.UserSkill;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {

    // Find skills that a user wants to learn
    @Query("SELECT us FROM UserSkill us WHERE us.user.id = :userId")
    List<UserSkill> findByUserId(Long userId);

    // Find skills a user can teach
    @Query("SELECT us FROM UserSkill us WHERE us.user.id = :userId")
    List<UserSkill> findSkillsUserCanTeach(Long userId);

    // Find all users who can teach a given skill
    @Query("SELECT us.user FROM UserSkill us WHERE us.teachSkill = :skillName")
    List<com.own.domain.model.User> findUsersTeachingSkill(String skillName);
}