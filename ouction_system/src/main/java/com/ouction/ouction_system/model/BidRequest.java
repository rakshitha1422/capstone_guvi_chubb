package com.ouction.ouction_system.model;

import lombok.Data;

@Data
public class BidRequest {
    private Long auctionId;
    private Long buyerId;
    private Double bidAmount;
}

