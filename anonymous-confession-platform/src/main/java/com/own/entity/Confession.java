package com.own.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "confessions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Confession {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(length = 1000)
    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private ConfessionStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // internal tracking only

    private String ipHash;
    private String deviceHash;

    private LocalDateTime createdAt = LocalDateTime.now();
}