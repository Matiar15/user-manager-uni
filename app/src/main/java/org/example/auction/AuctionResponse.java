package org.example.auction;

import java.time.LocalDateTime;

public record AuctionResponse(
        Integer id,
        String name,
        LocalDateTime startsAt,
        LocalDateTime endsAt,
        String description,
        Double price
) {
}
