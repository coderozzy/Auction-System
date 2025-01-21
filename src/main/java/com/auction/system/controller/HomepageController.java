package com.auction.system.controller;

import com.auction.system.service.AuctionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {

    private final AuctionService auctionService;

    public HomepageController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/")
    public String showHomePage(HttpSession session, Model model) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("auctions", auctionService.findAllAuctions());
        return "homepage";
    }


}
