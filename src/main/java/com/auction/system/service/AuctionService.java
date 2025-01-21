package com.auction.system.service;

import com.auction.system.entity.Auction;
import com.auction.system.entity.Item;
import com.auction.system.repository.AuctionRepository;
import com.auction.system.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final ItemRepository itemRepository;

    public AuctionService(AuctionRepository auctionRepository, ItemRepository itemRepository) {
        this.auctionRepository = auctionRepository;
        this.itemRepository = itemRepository;
    }

    public void createAuction(Auction auction) {
        auctionRepository.save(auction);
    }

    public List<Auction> getAuctionsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return auctionRepository.findByAuctionDateBetween(startOfDay, endOfDay);
    }

    public Auction getAuctionById(Long id) {
        return auctionRepository.findById(id).orElseThrow(() -> new RuntimeException("Auction not found"));
    }

    public List<Auction> findAllAuctions() {
        return auctionRepository.findAllByItemsNotNull();
    }


    public Auction addItemToAuction(Item item) {
        itemRepository.save(item);
        Auction auction = new Auction();
        auction.getItems().add(item);
        auction.setAuctionDate(LocalDate.now());
        return auctionRepository.save(auction);
    }

    public Auction editItemForBidding(Long auctionId, Item item) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new RuntimeException("Auction not found"));
        itemRepository.save(item);
        auction.getItems().add(item);
        return auctionRepository.save(auction);
    }

    public void removeItemFromAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new RuntimeException("Auction not found"));
        auctionRepository.delete(auction);
    }

    public void saveAuction(Auction auction) {
        auction.setAuctionDate(LocalDate.now());
        auction = auctionRepository.save(auction);
        for (Item item : auction.getItems()) {
            item.setAuction(auction);
            itemRepository.save(item);
        }
    }

    public void updateAuction(Long id, Auction auctionDetails) {
        Optional<Auction> auctionOptional = auctionRepository.findById(id);

        if (auctionOptional.isPresent()) {
            Auction auction = auctionOptional.get();
            auction.setName(auctionDetails.getName());
            auction.setAuctionDate(auctionDetails.getAuctionDate());
            auction.setStatus(auctionDetails.getStatus());

            // Clear existing items and add new items
            auction.getItems().clear();
            for (Item item : auctionDetails.getItems()) {
                item.setAuction(auction);
                auction.getItems().add(item);
            }

            auctionRepository.save(auction);
        }
    }

    public ByteArrayInputStream downloadAuctionInfo(Long auctionId) throws IOException {
        Optional<Auction> auctionOptional = auctionRepository.findById(auctionId);
        if (auctionOptional.isPresent()) {
            Auction auction = auctionOptional.get();
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            // Write auction info to the output stream
            out.write(("Auction ID: " + auction.getId() + "\n").getBytes());
            out.write(("Auction Name: " + auction.getName() + "\n").getBytes());
            out.write(("Auction Date: " + auction.getAuctionDate() + "\n").getBytes());
            out.write(("Auction Status: " + auction.getStatus() + "\n").getBytes());

            // Write items info to the output stream
            for (Item item : auction.getItems()) {
                out.write(("Item ID: " + item.getId() + "\n").getBytes());
                out.write(("Item Name: " + item.getName() + "\n").getBytes());
                out.write(("Base Price: " + item.getBasePrice() + "\n").getBytes());
                out.write("\n".getBytes());
            }

            return new ByteArrayInputStream(out.toByteArray());
        }
        return null;
    }
}
