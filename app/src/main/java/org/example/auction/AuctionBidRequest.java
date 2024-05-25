package org.example.auction;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AuctionBidRequest(
        @NotNull
        @Positive
        @DecimalMax("1000.0") @DecimalMin("0.0")
        BigDecimal price
) {
}
