package com.ouction.ouction_system.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(name = "userId")
    private int userId;

    @Column(name = "message")
    private String message;

    @Column(name = "type")
    private String type; // E.g., "BID_UPDATE" or "TIMER_ALERT"

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "is_read")
    private Boolean isRead;

    // Getters and setters
}

