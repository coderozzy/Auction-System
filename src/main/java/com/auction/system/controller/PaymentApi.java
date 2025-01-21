package com.auction.system.controller;

import com.auction.system.service.BidService;
import com.auction.system.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentApi {

    private final PaymentService paymentService;
    private final BidService bidService;

    public PaymentApi(PaymentService paymentService, BidService bidService) {
        this.paymentService = paymentService;
        this.bidService = bidService;
    }

    @PostMapping("/pay")
    public ResponseEntity<String> makePayment(@RequestBody PaymentRequest paymentRequest) {
        if (paymentService.isValidCardNumber(paymentRequest.getCardNumber())) {
            bidService.placeBidWithPayment(paymentRequest.getAuctionId(), paymentRequest.getAmount(), paymentRequest.getCardNumber(), paymentRequest.getUsername(), paymentRequest.getBidId());
            return ResponseEntity.ok("Payment and bid successful");
        } else {
            return ResponseEntity.badRequest().body("Invalid card number");
        }
    }

    public static class PaymentRequest {
        private Long auctionId;
        private Long bidId;
        private double amount;
        private String cardNumber;
        private String username;

        // Getters and setters
        public Long getAuctionId() {
            return auctionId;
        }

        public void setAuctionId(Long auctionId) {
            this.auctionId = auctionId;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Long getBidId() {
            return bidId;
        }

        public void setBidId(Long bidId) {
            this.bidId = bidId;
        }
    }
}