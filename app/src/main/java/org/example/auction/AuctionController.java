package org.example.auction;

import jakarta.validation.Valid;
import org.example.util.EntityMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public Page<AuctionResponse> fetchByFilter(
            @Valid AuctionFilterRequest filter,
            Pageable page
    ) {
        return auctionService.fetchByFilter(filter.asFilter(), page)
                .map(mapper::fromAuction);
    }
}
