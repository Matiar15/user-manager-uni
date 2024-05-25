package org.example.auction;

import jakarta.validation.constraints.*;
import org.example.validation.group.Patch;
import org.example.validation.group.Post;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AuctionRequest(
        @Size.List({
                @Size(max = 60, message = "auctionRequest.name.size.max", groups = {Post.class, Patch.class}),
                @Size(min = 10, message = "auctionRequest.name.size.min", groups = {Post.class, Patch.class})
        })
        @NotBlank(groups = {Post.class})
        String name,

        @NotNull(groups = {Post.class})
        LocalDateTime startsAt,

        @Size.List({
                @Size(max = 120, message = "auctionRequest.description.size.max", groups = {Post.class, Patch.class}),
                @Size(min = 10, message = "auctionRequest.description.size.min", groups = {Post.class, Patch.class})
        })
        @NotBlank(groups = {Post.class})
        String description,

        @NotNull(groups = {Post.class})
        @DecimalMax(value = "1000.0", groups = {Post.class, Patch.class})
        @DecimalMin(value = "0.0", groups = {Post.class, Patch.class})
        @Positive(groups = {Post.class, Patch.class})
        BigDecimal price
) {
}
