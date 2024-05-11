package org.example.auction;

import jakarta.validation.Valid;
import org.example.token.TokenService;
import org.example.util.EntityMapper;
import org.example.validation.group.Patch;
import org.example.validation.group.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {
    private final AuctionService auctionService;
    private final EntityMapper mapper;
    private final TokenService tokenService;

    public AuctionController(
            AuctionService auctionService,
            EntityMapper mapper,
            TokenService tokenService
    ) {
        this.auctionService = auctionService;
        this.mapper = mapper;
        this.tokenService = tokenService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void create(
            @Validated(Post.class) @RequestBody AuctionRequest request,
            @RequestHeader(AUTHORIZATION) String token
    ) {
        var userId = tokenService.getUserId(token);
        auctionService.createAuction(
                request.name(),
                request.startsAt(),
                request.description(),
                request.price(),
                userId
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
    @ResponseStatus(NO_CONTENT)
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
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable int id) {
        auctionService.deleteById(id);
    }


    @PatchMapping("/{id}/bid")
    @ResponseStatus(NO_CONTENT)
    public void patch(@PathVariable int id, AuctionBidRequest request,
                      @RequestHeader(AUTHORIZATION) String token
    ) {
        var userId = tokenService.getUserId(token);
        var email = tokenService.extractEmail(token);

        auctionService.bidOnAuction(
                id,
                userId,
                email,
                request.price()
        );
    }
}
