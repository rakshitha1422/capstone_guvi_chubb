package com.ouction.ouction_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTasks {
    @Autowired
    private AuctionService auctionService;

    @Scheduled(fixedRate = 60000) // Run every 60 seconds
    public void checkTimers() {
        auctionService.checkAuctionTimers();
    }
}

