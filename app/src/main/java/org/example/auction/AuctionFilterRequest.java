package org.example.auction;

import org.example.util.RangeDto;

import java.util.Collection;

public record AuctionFilterRequest(
        Collection<String> name,
        RangeDto.DateTimeRangeDto startsAt,
        RangeDto.DateTimeRangeDto endsAt
) {
    public AuctionFilter asFilter() {
        return new AuctionFilter(name, startsAt.asRange(), endsAt.asRange());
    }
}
