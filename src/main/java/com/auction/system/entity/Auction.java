package com.auction.system.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Bid highestBid;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    private List<Bid> bids = new ArrayList<>();

    private LocalDate auctionDate;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bid getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(Bid highestBid) {
        this.highestBid = highestBid;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public LocalDate getAuctionDate() {
        return auctionDate;
    }

    public void setAuctionDate(LocalDate auctionDate) {
        this.auctionDate = auctionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Auction() {
        this.items = new ArrayList<>();
    }
}
