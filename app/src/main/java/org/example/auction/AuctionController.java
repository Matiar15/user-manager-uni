package org.example.auction;

import jakarta.validation.Valid;
import org.example.EntityMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {
    private final AuctionService auctionService;
    private final EntityMapper mapper;

    public AuctionController(
            AuctionService auctionService,
            EntityMapper mapper
    ) {
        this.auctionService = auctionService;
        this.mapper = mapper;
    }

    @PostMapping
    public AuctionResponse create(@Valid @RequestBody AuctionRequest request) {
        return mapper.fromAuction(auctionService.createAuction(
                request.name(),
                request.startsAt(),
                request.description(),
                request.price()
        ));
    }
}
