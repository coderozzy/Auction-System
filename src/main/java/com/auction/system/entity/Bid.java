package com.auction.system.entity;

import jakarta.persistence.*;

@Entity
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @OneToOne
    @JoinColumn(name = "auction_id", insertable = false, updatable = false)
    private Auction highestBidAuction;


    public Bid(double amount, User user) {
        this.amount = amount;
        this.user = user;
    }

    public Bid() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public Auction getHighestBidAuction() {
        return highestBidAuction;
    }

    public void setHighestBidAuction(Auction highestBidAuction) {
        this.highestBidAuction = highestBidAuction;
    }
}
