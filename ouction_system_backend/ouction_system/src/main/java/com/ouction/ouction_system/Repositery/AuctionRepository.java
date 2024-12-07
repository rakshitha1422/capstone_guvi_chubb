package com.ouction.ouction_system.Repositery;

import com.ouction.ouction_system.model.Auction;
import com.ouction.ouction_system.model.SellerBidCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction,Long> {
    boolean existsByItemId(Long itemId);

    @Query("SELECT a FROM Auction a WHERE a.startTime <= :loginTime")
    List<Auction> findAvailableAuctions(@Param("loginTime") LocalDateTime loginTime);

    Auction findByItemIdAndStatus(Long itemId, String status);

    List<Auction> findByStatus(String status);

    // Sort by popularity (descending order of bid count)
    @Query("SELECT a FROM Auction a ORDER BY a.bidCount DESC")
    List<Auction> findAllByPopularity();

    // Sort by ending soon (ascending order of remaining time)
    @Query("SELECT a FROM Auction a WHERE a.endTime > CURRENT_TIMESTAMP ORDER BY a.endTime ASC")
    List<Auction> findAllEndingSoon();


    @Query("SELECT new com.ouction.ouction_system.model.SellerBidCountDto(a.sellerId, SUM(a.bidCount)) " +
            "FROM Auction a GROUP BY a.sellerId")
    List<SellerBidCountDto> getBidCountBySeller();


}
