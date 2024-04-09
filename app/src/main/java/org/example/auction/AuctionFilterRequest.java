package org.example.auction;

import jakarta.validation.constraints.NotBlank;
import org.example.util.RangeDto;

import java.util.Collection;

public record AuctionFilterRequest(
        Collection<@NotBlank String> name,
        RangeDto.DateTimeRangeDto startsAt,
        RangeDto.DateTimeRangeDto endsAt
) {
    public AuctionFilter asFilter() {
        return new AuctionFilter(
                name,
                startsAt == null ? null : startsAt.asRange(),
                endsAt == null ? null : endsAt.asRange()
        );
    }
}
