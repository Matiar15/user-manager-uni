package org.example.auction;

import org.example.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface AuctionService {
    Auction createAuction(
            String name,
            LocalDateTime startsAt,
            String description,
            BigDecimal price,
            int userId
    );

    Page<Auction> fetchByFilter(AuctionFilter filter, Pageable page);

    Auction patchAuction(
            int id,
            String name,
            String description,
            BigDecimal price,
            LocalDateTime localDateTime
    );

    void deleteById(int id);

    Auction bidOnAuction(int id, int winnerId, String winnerEmail, BigDecimal price);

    Auction requireById(int id);
}
