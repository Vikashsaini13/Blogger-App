package com.example.BloggerApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer followId;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User followerId;


    @ManyToOne
    @JoinColumn(name = "following_id", nullable = false)
    private User followingId;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
