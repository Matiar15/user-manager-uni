package org.example.auction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AuctionResponse(
        Integer id,
        String name,
        LocalDateTime startsAt,
        LocalDateTime endsAt,
        String description,
        BigDecimal startPrice,
        BigDecimal currentPrice,
        String winnerEmail,
        Integer winnerId,
        Integer ownerId
) {
}
