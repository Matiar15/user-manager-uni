package org.example.auction;

import org.example.Auction;

import java.time.LocalDateTime;

public interface AuctionService {
    Auction createAuction(
            String name,
            LocalDateTime startsAt,
            String description,
            Double price
    );

}
