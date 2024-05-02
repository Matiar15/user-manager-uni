package org.example.auction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.example.validation.group.Patch;
import org.example.validation.group.Post;

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
        @Positive(groups = {Post.class, Patch.class})
        Double price
) {
}
