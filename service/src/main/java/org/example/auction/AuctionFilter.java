package org.example.auction;

import com.google.common.collect.Range;

import java.time.LocalDateTime;
import java.util.Collection;

public record AuctionFilter(
        Collection<String> name,
        Range<LocalDateTime> startsAt,
        Range<LocalDateTime> endsAt
) {
}
