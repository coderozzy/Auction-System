package com.auction.system.service;

import com.auction.system.entity.Auction;
import com.auction.system.entity.Bid;
import com.auction.system.entity.User;
import com.auction.system.repository.AuctionRepository;
import com.auction.system.repository.BidRepository;
import com.auction.system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {

    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService;

    public BidService(BidRepository bidRepository, AuctionRepository auctionRepository, UserRepository userRepository, PaymentService paymentService) {
        this.bidRepository = bidRepository;
        this.auctionRepository = auctionRepository;
        this.userRepository = userRepository;
        this.paymentService = paymentService;
    }

    public void placeBid(Auction auction, Bid bid) {
        auction.getBids().add(bid);
        auction.setHighestBid(findHighestBid(auction.getBids()));
        auctionRepository.save(auction);
    }

    private Bid findHighestBid(List<Bid> bids) {
        Bid highestBid = bids.get(0);
        for (Bid bid : bids) {
            if (bid.getAmount() > highestBid.getAmount()) {
                highestBid = bid;
            }
        }
        return highestBid;
    }

    public void deleteBidById(Long bidId) {
        Bid bid = bidRepository.findById(bidId).orElseThrow(() -> new RuntimeException("Bid not found"));
        if (bid.getAuction().getHighestBid() != null && bid.getAuction().getHighestBid().getId().equals(bid.getId())) {
            bid.getAuction().setHighestBid(null);
        }
        bidRepository.delete(bid);
    }

    public void delete(Bid bid) {
        bid.setAmount(0.0d);
        bid.setUser(null);
        bid.setAuction(null);
        bid.setHighestBidAuction(null);
        bidRepository.save(bid);
    }

    public void placeBidWithPayment(Long auctionId, double amount, String cardNumber, String username, Long pidId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new RuntimeException("Auction not found"));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        if (pidId != null) {
            Bid bid = findById(pidId);
            bid.setAmount(amount);
            save(bid);
            return;
        }

        Bid bid = paymentService.processPayment(auction, cardNumber, amount, user);
        placeBid(auction, bid);
    }

    public Bid findByAuctionIdAndUserName(Long auctionId, String username) {
        return bidRepository.findByAuctionIdAndUserName(auctionId, username);
    }

    public Bid findById(Long bidId) {
        return bidRepository.findById(bidId).orElseThrow(() -> new RuntimeException("Bid not found"));
    }

    public void save(Bid bid) {
        bidRepository.save(bid);
    }
}
