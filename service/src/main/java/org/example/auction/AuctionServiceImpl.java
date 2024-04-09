package org.example.auction;

import jakarta.transaction.Transactional;
import org.example.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;

    public AuctionServiceImpl(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @Override
    public Auction createAuction(String name, LocalDateTime startsAt, String description, Double price) {
        return auctionRepository.save(toAuction(name, startsAt, description, price));
    }

    @Override
    public Page<Auction> fetchByFilter(AuctionFilter filter, Pageable page) {
        return auctionRepository.fetchByFilter(filter, page);
    }

    private Auction toAuction(String name, LocalDateTime startsAt, String description, Double price) {
        var auction = new Auction();
        auction.setActive(true);
        auction.setEndsAt(startsAt.plusHours(3));
        auction.setDescription(description);
        auction.setPrice(price);
        auction.setName(name);
        auction.setStartsAt(startsAt);
        return auction;
    }
}
