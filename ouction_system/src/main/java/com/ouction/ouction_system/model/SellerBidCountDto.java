package com.ouction.ouction_system.model;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SellerBidCountDto {
    private int sellerId;
    private Long totalBidCount;
}
