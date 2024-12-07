package com.ouction.ouction_system.controller;

import com.ouction.ouction_system.Repositery.NotificationRepository;
import com.ouction.ouction_system.model.*;
import com.ouction.ouction_system.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {
    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    private AuctionService auctionService;



    @PostMapping("/register")
    public ResponseEntity<String> registerAuction(@RequestBody AuctionRequest auctionRequest) {
        return auctionService.registerAuction(auctionRequest);
    }


    @GetMapping("/filter/popularity")
    public ResponseEntity<List<AuctionDetailsResponse>> filterByPopularity() {
        List<AuctionDetailsResponse> auctions = auctionService.getAuctionsByPopularity();
        return ResponseEntity.ok(auctions);
    }

    @GetMapping("/filter/ending-soon")
    public ResponseEntity<List<AuctionDetailsResponse>> filterByEndingSoon() {
        List<AuctionDetailsResponse> auctions = auctionService.getAuctionsEndingSoon();
        return ResponseEntity.ok(auctions);
    }



    // Endpoint to fetch buyer ID by username
    @GetMapping("/getBuyerIdByUsername")
    public ResponseEntity<?> getBuyerIdByUsername(@RequestParam String username) {
        try {
            Integer buyerId = auctionService.getBuyerIdByUsername(username);
            if (buyerId != null) {
                return ResponseEntity.ok(new BuyerIdResponse(buyerId));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Buyer not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching buyer ID");
        }
    }


    @GetMapping("/available-items")
    public ResponseEntity<List<AuctionDetailsResponse>> getAvailableAuctions(@RequestParam String buyerLoginTime) {
        List<AuctionDetailsResponse> availableAuctions = auctionService.getAvailableAuctions(buyerLoginTime);
        return ResponseEntity.ok(availableAuctions);
    }

    @PostMapping("/placeBid")
    public ResponseEntity<String> placeBid(@RequestBody BidRequest bidRequest) {
        boolean bidSuccess = auctionService.placeBid(bidRequest);
        if (bidSuccess) {
            return ResponseEntity.ok("Bid placed successfully!");
        } else {
            return ResponseEntity.badRequest().body("Bid amount must be higher than the current bid.");
        }
    }

    @PutMapping("/close/{auctionId}")
    public ResponseEntity<String> closeAuction(@PathVariable Long auctionId) {
        return auctionService.closeAuction(auctionId);
    }

    @GetMapping("/notifications/{userId}")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndIsReadFalse(userId);
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/notifications/{notificationId}/read")
    public ResponseEntity<String> markAsRead(@PathVariable Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
        return ResponseEntity.ok("Notification marked as read");
    }

    @GetMapping("/{auctionId}/lastBidder")
    public ResponseEntity<?> getLastBidder(@PathVariable Long auctionId) {
        try {
            Optional<Bid> lastBid = auctionService.getLastBid(auctionId);
            if (lastBid.isPresent()) {
                return ResponseEntity.ok(new LastBidderResponse(
                        lastBid.get().getBuyerId(),
                        lastBid.get().getBidAmount()
                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No bids found for this auction.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching last bidder.");
        }
    }

    @PostMapping("/{auctionId}/purchase")
    public ResponseEntity<String> purchaseItem(@PathVariable Long auctionId, @RequestBody PurchaseRequest purchaseRequest) {
        return auctionService.purchaseItem(auctionId, purchaseRequest);
    }



    @PostMapping("/submitDispute")
    public ResponseEntity<?> submitDispute(@RequestBody DisputeRequest disputeRequest) {
        try {
            Integer buyerId = auctionService.getBuyerIdByUsername(disputeRequest.getUsername());
            if (buyerId == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Buyer not found");
            }
            auctionService.saveDispute(disputeRequest.getAuctionId(), buyerId, disputeRequest.getIssue());
            return ResponseEntity.ok("Dispute submitted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting dispute.");
        }
    }



    @GetMapping("/disputes")
    public ResponseEntity<?> getOpenDisputes() {
        try {
            List<Dispute> openDisputes = auctionService.getOpenDisputes();
            return ResponseEntity.ok(openDisputes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching open disputes.");
        }
    }




    @PutMapping("/resolveDispute")
    public ResponseEntity<?> resolveDispute(@RequestBody Map<String, Long> request) {
        Long disputeId = request.get("disputeId");
        try {
            auctionService.resolveDispute(disputeId);
            return ResponseEntity.ok("Dispute resolved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error resolving dispute.");
        }
    }



    @GetMapping("/details")
    public ResponseEntity<List<BidCardDto>> getBidDetails() {
        return ResponseEntity.ok(auctionService.getBidDetails());
    }


    @GetMapping("/seller-bid-counts")
    public List<CountRequest> getSellerBidCounts() {
        return auctionService.getSellerBidCounts();
    }

    @GetMapping("/getsellerdetails")
    public List<ClosedAuctionDto> getsellerddertails(){
        return auctionService.getSellerDetails();
    }


}

