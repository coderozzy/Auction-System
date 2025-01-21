package com.auction.system.repository;

import com.auction.system.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findByAuctionDateAfter(LocalDateTime date);

    List<Auction> findByAuctionDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Auction> findByAuctionDate(LocalDateTime date);

    List<Auction> findAllByItemsNotNull();
}
