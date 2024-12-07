package com.ouction.ouction_system.model;

import java.time.LocalDateTime;

public class BidCardDto {
    private String buyerName;
    private Double bidAmount;
    private LocalDateTime bidTime;



    // Getters and setters
    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(Double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public LocalDateTime getBidTime() {
        return bidTime;
    }

    public void setBidTime(LocalDateTime bidTime) {
        this.bidTime = bidTime;
    }
}