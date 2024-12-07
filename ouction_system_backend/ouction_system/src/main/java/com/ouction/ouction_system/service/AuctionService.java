package com.ouction.ouction_system.service;

import com.ouction.ouction_system.Repositery.*;
import com.ouction.ouction_system.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UserRepositery userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EmailService emailService;


    @Autowired
    private DisputeRepository disputeRepository;




    // Push Notification Logic
    public void sendNotification(int userId, String message, String type) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setType(type);
        notification.setTimestamp(LocalDateTime.now());
        notification.setIsRead(false);
        notificationRepository.save(notification);
    }



    public ResponseEntity<String> registerAuction(AuctionRequest request) {
        // Get the user and item details

        Auction existingAuction = auctionRepository.findByItemIdAndStatus(request.getItemId(), "OPEN");


        if (existingAuction != null) {
            return ResponseEntity.badRequest().body("Item is already registered in an open auction.");
        }


        User seller = userRepository.findByUsername(request.getUsername());
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));


        // Create auction
        Auction auction = new Auction();
        auction.setSellerId(seller.getUserid());
        auction.setItemId(item.getItemId());
        auction.setStartingPrice(request.getStartPrice());
        auction.setReservePrice(request.getReservePrice());
        auction.setStartTime(request.getStartBidTime());
        auction.setEndTime(request.getEndBidTime());
        auction.setStatus("OPEN");


        auctionRepository.save(auction);
        return ResponseEntity.ok("Auction registered successfully.");
    }


    public Integer getBuyerIdByUsername(String username) {
        // Query database to find the buyer by username
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getUserid();  // Return the buyer ID
        }
        return null;  // Return null if user is not found
    }

    @Autowired
    private BidRepository bidRepository;


    public List<AuctionDetailsResponse> getAvailableAuctions(String buyerLoginTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        ZonedDateTime utcLoginTime = ZonedDateTime.parse(buyerLoginTime, formatter);
        LocalDateTime loginTime = utcLoginTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println(loginTime);

        List<Auction> auctions = auctionRepository.findAvailableAuctions(loginTime);
        //System.out.println(auctions);
        return auctions.stream().map(auction -> {
            Item item = itemRepository.findById(auction.getItemId())
                    .orElseThrow(() -> new RuntimeException("Item not found for auction: " + auction.getAuctionId()));

            return new AuctionDetailsResponse(
                    auction.getAuctionId(),
                    auction.getSellerId(),
                    item.getName(),
                    item.getCategory(),
                    item.getImage(),
                    auction.getStartingPrice(),
                    auction.getStartTime(),
                    auction.getEndTime(),
                    auction.getCurrentBid()
            );
        }).collect(Collectors.toList());
    }

    public boolean placeBid(BidRequest bidRequest) {

            Optional<Auction> auction = auctionRepository.findById(bidRequest.getAuctionId());

            System.out.println("bidamount"+bidRequest.getBidAmount());
            System.out.println("currenbid"+auction.get().getCurrentBid());
            if (bidRequest.getBidAmount() <= auction.get().getCurrentBid()) {
                return false;
            }

            // Update auction current bid and bid count
            auction.get().setCurrentBid(bidRequest.getBidAmount());
            auction.get().setBidCount(auction.get().getBidCount() + 1);
            auctionRepository.save(auction.get());

            // Add a new entry in the Bids table
            Bid bid = new Bid();
            bid.setAuctionId(bidRequest.getAuctionId());
            bid.setBuyerId(bidRequest.getBuyerId());
            bid.setBidAmount(bidRequest.getBidAmount());
            bid.setBidTime(LocalDateTime.now());
            bidRepository.save(bid);





            // Send notifications to all buyers
        Optional<User> buyerOptional = userRepository.findById(Math.toIntExact(bidRequest.getBuyerId()));
        if (buyerOptional.isPresent()) {
            User buyer = buyerOptional.get();
            sendNotification(
                    buyer.getUserid(),
                    String.format("New bid placed: Rs.%.2f by %s", bidRequest.getBidAmount(), buyer.getUsername()),
                    "BID_UPDATE"
            );
        }

        // Send email to all buyers
        List<User> buyers = userRepository.findByUserrole("Buyer"); // Fetch only buyers




        if (auction.isPresent()) {
            Auction auctionDetails = auction.get(); // Get auction details
            int itemId = Math.toIntExact(auctionDetails.getItemId()); // Fetch the item ID

            // Fetch the item name from the items table using the item ID
            Optional<Item> itemOptional = itemRepository.findById((long) itemId);
            if (itemOptional.isPresent()) {
                String itemName = itemOptional.get().getName(); // Get item name

                for (User buyer : buyers) {
                    User buyeremail = buyerOptional.get();
                    emailService.sendEmail(
                            buyer.getUseremail(),
                            "New Bid Placed in Auction!",
                            String.format("A new bid of Rs.%.2f has been placed by %s in the auction for item %s.",
                                    bidRequest.getBidAmount(), buyeremail.getUsername(), itemName)
                    );
                }
            } else {
                // Handle case where item is not found
                throw new RuntimeException("Item not found for the given itemId: " + itemId);
            }
        } else {
            // Handle case where the auction does not exist
            throw new RuntimeException("Auction not found");
        }




            return true;

    }

    public ResponseEntity<String> closeAuction(Long auctionId) {
        // Fetch the auction by ID
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found"));

        // Check if the auction is already closed
        if (!"CLOSED".equalsIgnoreCase(auction.getStatus())) {
            // Update the auction status to CLOSED
            auction.setStatus("CLOSED");
            auctionRepository.save(auction);
            return ResponseEntity.ok("Auction closed successfully.");
        }
        else {
            return ResponseEntity.ok("Auction was already closed.");
        }

    }


    public void checkAuctionTimers() {
        List<Auction> openAuctions = auctionRepository.findByStatus("OPEN");
        for (Auction auction : openAuctions) {
            long timeRemaining = ChronoUnit.MINUTES.between(LocalDateTime.now(), auction.getEndTime());
            if (timeRemaining <= 10 && timeRemaining > 0) {
                List<User> buyers = userRepository.findAll(); // Notify all buyers
                for (User buyer : buyers) {
                    sendNotification(
                            buyer.getUserid(),
                            String.format("Auction for %s is ending in %d minutes!", auction.getItemId(), timeRemaining),
                            "TIMER_ALERT"
                    );
                }
            }
        }
    }


    public Optional<Bid> getLastBid(Long auctionId) {
        return bidRepository.findTopByAuctionIdOrderByBidTimeDesc(auctionId);
    }

    public ResponseEntity<String> purchaseItem(Long auctionId, PurchaseRequest purchaseRequest) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found"));

        if (!"CLOSED".equals(auction.getStatus())) {
            return ResponseEntity.badRequest().body("Auction is still open. Purchase cannot be completed.");
        }

        Optional<Bid> lastBid = getLastBid(auctionId);
        if (lastBid.isPresent() && lastBid.get().getBuyerId().equals(purchaseRequest.getBuyerId())) {
            // Logic for marking the item as purchased
            return ResponseEntity.ok("Purchase of ₹" + lastBid.get().getBidAmount() + " is placed successfully!");
        } else {
            return ResponseEntity.badRequest().body("You are not eligible to purchase this item.");
        }
    }


    public List<AuctionDetailsResponse> getAuctionsByPopularity() {
        List<Auction> auctions = auctionRepository.findAllByPopularity();
        return transformToAuctionDetailsResponse(auctions);
    }

    public List<AuctionDetailsResponse> getAuctionsEndingSoon() {
        List<Auction> auctions = auctionRepository.findAllEndingSoon();
        return transformToAuctionDetailsResponse(auctions);
    }

    private List<AuctionDetailsResponse> transformToAuctionDetailsResponse(List<Auction> auctions) {
        List<AuctionDetailsResponse> responses = new ArrayList<>();
        for (Auction auction : auctions) {
            Item item = itemRepository.findById(auction.getItemId())
                    .orElseThrow(() -> new RuntimeException("Item not found for auction: " + auction.getAuctionId()));
            // Transform each Auction entity to AuctionDetailsResponse
            responses.add(new AuctionDetailsResponse(
                    auction.getAuctionId(),
                    auction.getSellerId(),
                    item.getName(),  //  itemName is fetched from the itemId
                    item.getCategory(),             //  itemCategory is stored in the status field
                    item.getImage(), //  itemImage is derived from itemId
                    auction.getStartingPrice(),
                    auction.getStartTime(),
                    auction.getEndTime(),
                    auction.getCurrentBid()
            ));
        }
        return responses;
    }



    public void saveDispute(Long auctionId, Integer userId, String issue) {
        Dispute dispute = new Dispute();
        dispute.setAuctionId(auctionId);
        dispute.setUserId(userId);
        dispute.setIssue(issue);
        disputeRepository.save(dispute);
    }


    public List<Dispute> getOpenDisputes() {
        return disputeRepository.findByStatus("Open");
    }


    public void resolveDispute(Long disputeId) throws Exception {
        // Fetch the dispute
        Dispute dispute = disputeRepository.findById(disputeId)
                .orElseThrow(() -> new Exception("Dispute not found"));

        // Update the resolution and status
        dispute.setResolution("Resolved");
        dispute.setStatus("Closed");
        disputeRepository.save(dispute);

        // Fetch the buyer's email
        User buyer = userRepository.findById(dispute.getUserId())
                .orElseThrow(() -> new Exception("User not found"));
        String buyerEmail = buyer.getUseremail();

        // Send email to the buyer
        emailService.sendEmail(buyerEmail, "Dispute Resolved", "Your issue is resolved.");
    }



    public List<BidCardDto> getBidDetails() {
        // Fetch all bids from the repository
        List<Bid> bids = bidRepository.findAll();

        // Create a list to store BidCardDto objects
        List<BidCardDto> bidCardDtos = new ArrayList<>();

        // Iterate through each bid and fetch user details
        for (Bid bid : bids) {
            // Fetch user details using buyerId from the bid
            User user = userRepository.findById(Math.toIntExact(bid.getBuyerId()))
                    .orElse(null); // Handle potential null case

            if (user != null) {
                // Map Bid and User details to BidCardDto
                BidCardDto bidCardDto = new BidCardDto();
                bidCardDto.setBidAmount(bid.getBidAmount());
                bidCardDto.setBidTime(bid.getBidTime());
                bidCardDto.setBuyerName(user.getUsername());

                // Add the DTO to the result list
                bidCardDtos.add(bidCardDto);
            }
        }

        // Return the list of BidCardDto objects
        return bidCardDtos;
    }



    public List<CountRequest> getSellerBidCounts() {
        // Fetch all seller bid counts
        List<SellerBidCountDto> bidCounts = auctionRepository.getBidCountBySeller();
        List<CountRequest> res = new ArrayList<>();

        // Map each SellerBidCountDto to CountRequest
        for (SellerBidCountDto bid : bidCounts) {
            // Fetch the seller's name from UserRepository
            Optional<User> userOptional = userRepository.findById(bid.getSellerId());
            String sellerName = userOptional.map(User::getUsername).orElse("Unknown Seller");

            // Create CountRequest object and add it to the result list
            res.add(new CountRequest(sellerName, bid.getTotalBidCount()));
        }
        return res;

    }

    public List<ClosedAuctionDto> getSellerDetails(){
        List<Auction> auctions=auctionRepository.findByStatus("CLOSED");
        List<ClosedAuctionDto> res=new ArrayList<>();
        for(Auction auction:auctions){
            User user=userRepository.getById(auction.getSellerId());
            String sellername=user.getUsername();
            Item item=itemRepository.getById(auction.getItemId());
            String itemname=item.getName();
            Double revenue=(auction.getCurrentBid()-auction.getStartingPrice());
            res.add(new ClosedAuctionDto(sellername,itemname,revenue));
        }
        return res;
    }




}

