package org.example.util;

import org.example.Auction;
import org.example.auction.AuctionResponse;
import org.springframework.stereotype.Service;

@Service
public class EntityMapper {
    public AuctionResponse fromAuction(Auction auction) {
        return new AuctionResponse(
                auction.getId(),
                auction.getName(),
                auction.getStartsAt(),
                auction.getEndsAt(),
                auction.getDescription(),
                auction.getStartPrice(),
                auction.getCurrentPrice(),
                auction.getWinnersEmail(),
                auction.getWinnersId(),
                auction.getOwnerId()
        );
    }
}
