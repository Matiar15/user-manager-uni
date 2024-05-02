package org.example.auction;

import org.example.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface AuctionService {
    Auction createAuction(
            String name,
            LocalDateTime startsAt,
            String description,
            Double price
    );

    Page<Auction> fetchByFilter(AuctionFilter filter, Pageable page);

    Auction patchAuction(
            int id,
            String name,
            String description,
            Double price,
            LocalDateTime localDateTime
    );
}
