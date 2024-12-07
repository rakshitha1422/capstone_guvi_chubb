package com.ouction.ouction_system.Repositery;

import com.ouction.ouction_system.model.Bid;
import com.ouction.ouction_system.model.BidCardDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    Optional<Bid> findTopByAuctionIdOrderByBidTimeDesc(Long auctionId);




}
