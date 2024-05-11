package org.example.auction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AuctionBidRequest(
        @NotNull
        @Positive
        Double price
) {
}
