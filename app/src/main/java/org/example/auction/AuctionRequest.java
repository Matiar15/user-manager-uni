package org.example.auction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record AuctionRequest(
        @Size.List({
                @Size(max = 60, message = "auctionRequest.name.size.max"),
                @Size(min = 10, message = "auctionRequest.name.size.min")
        })
        @NotBlank
        String name,

        @NotNull
        LocalDateTime startsAt,

        @Size.List({
                @Size(max = 120, message = "auctionRequest.description.size.max"),
                @Size(min = 10, message = "auctionRequest.description.size.min")
        })
        @NotBlank
        String description,

        @NotNull
        @Positive
        Double price
) {
}
