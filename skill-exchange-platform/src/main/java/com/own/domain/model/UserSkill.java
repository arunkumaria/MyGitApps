package com.own.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class UserSkill {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    private String teachSkill;   // Skill user can teach
    private String learnSkill;   // Skill user wants to learn

    private String level;        // Beginner / Intermediate / Advanced
}