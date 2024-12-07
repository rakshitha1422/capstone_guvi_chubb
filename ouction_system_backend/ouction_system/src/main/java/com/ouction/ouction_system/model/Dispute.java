package com.ouction.ouction_system.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "disputes")
@Data
public class Dispute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long disputeId;

    private Long auctionId;
    private Integer userId; // BuyerId
    private String issue;
    private String resolution = "Pending"; // Default resolution status
    private String status = "Open"; // Default status
}

