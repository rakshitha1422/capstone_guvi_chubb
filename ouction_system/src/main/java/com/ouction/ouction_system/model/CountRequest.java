package com.ouction.ouction_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountRequest {
    String username;
    Long bidCount;
}
