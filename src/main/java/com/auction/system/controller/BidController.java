package com.auction.system.controller;

import com.auction.system.entity.Auction;
import com.auction.system.entity.Bid;
import com.auction.system.entity.User;
import com.auction.system.service.AuctionService;
import com.auction.system.service.BidService;
import com.auction.system.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BidController {

    private final BidService bidService;
    private final AuctionService auctionService;
    private final UserService userService;

    public BidController(BidService bidService, AuctionService auctionService, UserService userService) {
        this.bidService = bidService;
        this.auctionService = auctionService;
        this.userService = userService;
    }

    @GetMapping("/bid/{auctionId}")
    public String showBidForm(@PathVariable("auctionId") Long auctionId, Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        Auction auction = auctionService.getAuctionById(auctionId);
        Bid bid = bidService.findByAuctionIdAndUserName(auctionId, username);
        if (bid == null) {
            bid = new Bid();
        }
        model.addAttribute("auction", auction);
        model.addAttribute("bid", bid);
        model.addAttribute("username", username);
        return "bidForm";
    }

    @PostMapping("/bid")
    public String placeBid(@RequestParam("auctionId") Long auctionId, @RequestParam("amount") double amount, @RequestParam("bidId") Long bidId, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        User user = userService.getUserByUsername(username);
        Auction auction = auctionService.getAuctionById(auctionId);
        if (bidId != null) {
            Bid bid = bidService.findById(bidId);
            bid.setAmount(amount);
            bidService.save(bid);
            return "redirect:/";
        }
        Bid bid = new Bid(amount, user);
        bid.setAuction(auction);
        bidService.placeBid(auction, bid);
        return "redirect:/";
    }
}
