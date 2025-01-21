package com.auction.system.controller;

import com.auction.system.entity.Auction;
import com.auction.system.entity.Item;
import com.auction.system.service.AuctionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/auction")
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/searchAuctions")
    public String searchAuctionsByDate(@RequestParam("date") String date, Model model, HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
        LocalDate localDate = LocalDate.parse(date);
        List<Auction> auctions = auctionService.getAuctionsByDate(localDate);
        model.addAttribute("auctions", auctions);
        model.addAttribute("username", session.getAttribute("username"));
        return "homepage";
    }

    // Display Add Auction Page
    @GetMapping("/add")
    public String showAddAuctionPage(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null || !username.equals("admin")) {
            return "redirect:/";
        }

        model.addAttribute("auction", new Auction());
        model.addAttribute("items", new Item());
        return "add-auction";
    }

    // Adding an item to an auction
    @PostMapping("/add")
    public ResponseEntity<String> addItemToAuction(@RequestBody Auction auction, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null || !username.equals("admin")) {
            return ResponseEntity.status(403).body("Only admin can add items to auction.");
        }

        auctionService.saveAuction(auction);
        return ResponseEntity.ok("Auction added successfully.");
    }

    @GetMapping("/details/{id}")
    public String getAuctionDetails(@PathVariable("id") Long id, Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null || !username.equals("admin")) {
            return "redirect:/";
        }

        Auction auction = auctionService.getAuctionById(id);
        model.addAttribute("auction", auction);
        model.addAttribute("username", username);
        return "auctions-details";
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAuction(@PathVariable("id") Long id, @RequestBody Auction auctionDetails) {
        auctionService.updateAuction(id, auctionDetails);
        return ResponseEntity.ok("Auction updated successfully");
    }

}
