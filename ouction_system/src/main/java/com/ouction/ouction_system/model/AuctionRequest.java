package com.ouction.ouction_system.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuctionRequest {
    private Long itemId;
    private String username;
    private Double startPrice;
    private Double reservePrice;
    private LocalDateTime startBidTime;
    private LocalDateTime endBidTime;
}

