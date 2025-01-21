package com.auction.system.repository;

import com.auction.system.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query("SELECT b FROM Bid b WHERE b.auction.id = :auctionId AND b.user.username = :username")
    Bid findByAuctionIdAndUserName(@Param("auctionId") Long auctionId, @Param("username") String username);
}
