package org.example.auction;

import jakarta.validation.Valid;
import org.example.validation.group.Patch;
import org.example.validation.group.Post;
import org.example.util.EntityMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Validated(Post.class) @RequestBody AuctionRequest request) {
        auctionService.createAuction(
                request.name(),
                request.startsAt(),
                request.description(),
                request.price()
        );
    }

    @GetMapping
    public Page<AuctionResponse> fetchByFilter(
            @Valid AuctionFilterRequest filter,
            Pageable page
    ) {
        return auctionService.fetchByFilter(filter.asFilter(), page).map(mapper::fromAuction);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable int id, @Validated(Patch.class) @RequestBody AuctionRequest request) {
        auctionService.patchAuction(
                id,
                request.name(),
                request.description(),
                request.price(),
                request.startsAt()
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        auctionService.deleteById(id);
    }
}
