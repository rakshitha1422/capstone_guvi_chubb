package com.ouction.ouction_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClosedAuctionDto {
    private String sellerName;
    private String itemName;
    private Double revenue;

}
