package com.ouction.ouction_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LastBidderResponse {
    private Long buyerId;
    private Double bidAmount;
    // Getters and setters
}
