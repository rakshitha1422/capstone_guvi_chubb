package com.ouction.ouction_system.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "items")
@Data
public class Item {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;



    private Long auctionId;

    private String name;
    private String description;


    private String image;

    private String category;
    private String tags;

    private LocalDateTime addedDate;





    // Getters and Setters
}
