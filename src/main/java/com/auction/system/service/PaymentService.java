package com.auction.system.service;

import com.auction.system.entity.Auction;
import com.auction.system.entity.Bid;
import com.auction.system.entity.User;
import com.auction.system.repository.AuctionRepository;
import com.auction.system.repository.BidRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;

    public PaymentService(AuctionRepository auctionRepository, BidRepository bidRepository) {
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
    }

    public void makePayment(Long auctionId, String paymentDetails) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new RuntimeException("Auction not found"));
        auction.setStatus("PAID");
        auctionRepository.save(auction);
    }

    public boolean isValidCardNumber(String cardNumber) {
        return cardNumber.matches("\\d{16}");
    }

    public Bid processPayment(Auction auction, String cardNumber, double amount, User user) {
        Bid bid = new Bid();
        bid.setAmount(amount);
        bid.setUser(user);
        bid.setAuction(auction);
        bid = bidRepository.save(bid);
        auction.setStatus("PAID");
        auctionRepository.save(auction);
        return bid;
    }
}
