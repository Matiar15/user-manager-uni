package org.example.auction;

import com.google.common.collect.Lists;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.example.util.RangeDto;

import java.util.Collection;

public record AuctionFilterRequest(
        Collection<@NotBlank String> name,
        RangeDto.DateTimeRangeDto startsAt,
        RangeDto.DateTimeRangeDto endsAt,
        Collection<@NotNull @PositiveOrZero Integer> ownerId
) {
    public AuctionFilter asFilter(Integer ownersId) {
        return new AuctionFilter(
                name,
                startsAt == null ? null : startsAt.asRange(),
                endsAt == null ? null : endsAt.asRange(),
                ownersId != null ? Lists.newArrayList(ownersId) : ownerId()
        );
    }
}
