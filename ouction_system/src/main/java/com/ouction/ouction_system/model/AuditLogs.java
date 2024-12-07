package com.ouction.ouction_system.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
public class AuditLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @Column(name = "user_name")
    private String username;
    private String action;
    private LocalDateTime timestamp;

    @Column(columnDefinition = "TEXT") // For longer detail text
    private String detail;


}

