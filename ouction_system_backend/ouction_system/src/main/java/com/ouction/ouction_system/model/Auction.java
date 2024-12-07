package com.ouction.ouction_system.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "auction")
@Data
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auctionId;

    private int sellerId;
    private Long itemId;
    private Double startingPrice;
    private Double reservePrice;
    private Double currentBid = 0.0;
    private Integer bidCount = 0;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;

}

