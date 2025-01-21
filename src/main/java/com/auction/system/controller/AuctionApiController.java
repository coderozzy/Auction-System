package com.auction.system.controller;

import com.auction.system.entity.Auction;
import com.auction.system.entity.Item;
import com.auction.system.service.AuctionService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/auctions")
public class AuctionApiController {

    private final AuctionService auctionService;

    public AuctionApiController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PutMapping("/edit/{auctionId}")
    public ResponseEntity<Auction> editItemForBidding(@PathVariable Long auctionId, @RequestBody Item item) {
        Auction auction = auctionService.editItemForBidding(auctionId, item);
        return ResponseEntity.ok(auction);
    }

    @DeleteMapping("/remove/{auctionId}")
    public ResponseEntity<String> removeItemFromAuction(@PathVariable Long auctionId) {
        auctionService.removeItemFromAuction(auctionId);
        return ResponseEntity.ok("Item removed from auction");
    }

    @GetMapping("/info/{auctionId}")
    public ResponseEntity<Auction> getItemInfo(@PathVariable Long auctionId) {
        Auction auction = auctionService.getAuctionById(auctionId);
        return ResponseEntity.ok(auction);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Auction>> getAllAuctions() {
        List<Auction> auctions = auctionService.findAllAuctions();
        return ResponseEntity.ok(auctions);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> downloadAuctionInfo(@PathVariable Long id) {
        ByteArrayInputStream bis;
        try {
            bis = auctionService.downloadAuctionInfo(id);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=auction_info.txt");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(bis));
    }
}
