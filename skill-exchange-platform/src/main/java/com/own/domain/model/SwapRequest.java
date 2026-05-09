package com.own.domain.model;

import com.own.domain.model.enums.SwapStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class SwapRequest {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User requester;

    @ManyToOne
    private User receiver;

    @ManyToOne
    private Skill skillOffered;

    @ManyToOne
    private Skill skillWanted;

    @Enumerated(EnumType.STRING)
    private SwapStatus status;
}