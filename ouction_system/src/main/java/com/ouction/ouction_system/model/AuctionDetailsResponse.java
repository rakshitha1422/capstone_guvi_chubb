package com.ouction.ouction_system.model;


import java.time.LocalDateTime;

public class AuctionDetailsResponse {
    private Long auctionId;
    private int sellerId;
    private String itemName;
    private String itemCategory;
    private String itemImage;
    private double startingPrice;
    private LocalDateTime bidStartTime;
    private LocalDateTime bidEndTime;
    private double currentBid;

    // Constructor
    public AuctionDetailsResponse(Long auctionId, int sellerId, String itemName, String itemCategory, String itemImage,
                                  double startingPrice, LocalDateTime bidStartTime, LocalDateTime bidEndTime, double currentBid) {
        this.auctionId = auctionId;
        this.sellerId = sellerId;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemImage = itemImage;
        this.startingPrice = startingPrice;
        this.bidStartTime = bidStartTime;
        this.bidEndTime = bidEndTime;
        this.currentBid = currentBid;
    }

    // Getters and Setters
    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public LocalDateTime getBidStartTime() {
        return bidStartTime;
    }

    public void setBidStartTime(LocalDateTime bidStartTime) {
        this.bidStartTime = bidStartTime;
    }

    public LocalDateTime getBidEndTime() {
        return bidEndTime;
    }

    public void setBidEndTime(LocalDateTime bidEndTime) {
        this.bidEndTime = bidEndTime;
    }

    public double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
    }
}

