package com.ouction.ouction_system.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "bids")
@Data
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bidId;

    private Long auctionId;
    private Long buyerId;
    private Double bidAmount;
    private LocalDateTime bidTime;
}

