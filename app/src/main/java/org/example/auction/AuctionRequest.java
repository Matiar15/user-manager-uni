package org.example.auction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record AuctionRequest(
        @Size.List({
                @Size(max = 60),
                @Size(min = 10)
        })
        @NotBlank
        String name,

        @NotNull
        LocalDateTime startsAt,

        @Size.List({
                @Size(max = 120),
                @Size(min = 10)
        })
        @NotBlank
        String description,

        @NotNull
        @Positive
        Double price
) {
}
