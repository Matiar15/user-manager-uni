package org.example.auction;

import jakarta.transaction.Transactional;
import org.example.Auction;
import org.example.exception.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;

    public AuctionServiceImpl(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @Override
    public Auction createAuction(
            String name,
            LocalDateTime startsAt,
            String description,
            BigDecimal price,
            int userId
    ) {
        return auctionRepository.save(toAuction(name, startsAt, description, price, userId));
    }

    @Override
    public Page<Auction> fetchByFilter(AuctionFilter filter, Pageable page) {
        return auctionRepository.fetchByFilter(filter, page);
    }

    @Override
    public Auction patchAuction(int id, String name, String description, BigDecimal price, LocalDateTime startsAt) {
        var auction = auctionRepository.requireById(id);
        var now = LocalDateTime.now();

        if (auction.getStartsAt().isBefore(now)) {
            throw new AuctionAlreadyStartedException(auction.getId());
        }

        if (name != null) auction.setName(name);
        if (description != null) auction.setDescription(description);
        if (price != null) auction.setStartPrice(price);
        if (startsAt != null) {
            if (startsAt.isBefore(now)) {
                throw new StartsAtExpiredException(startsAt);
            }
            auction.setStartsAtAndRenewEndsAt(startsAt);
        }
        return auction;
    }

    @Override
    public void deleteById(int id) {
        var auction = auctionRepository.requireById(id);

        if (auction.getStartsAt().isBefore(LocalDateTime.now())) {
            throw new AuctionAlreadyStartedException(auction.getId());
        }

        auctionRepository.delete(auction);
    }

    @Override
    public Auction bidOnAuction(int id, int winnerId, String winnerEmail, BigDecimal price) {
        var auction = auctionRepository.requireById(id);
        var now = LocalDateTime.now();

        if (auction.getCurrentPrice().doubleValue() >= price.doubleValue()) {
            throw new AuctionPriceException(auction.getId(), price.doubleValue());
        }

        if (now.isBefore(auction.getStartsAt())) {
            throw new AuctionNotStartedException(auction.getId());
        }

        if (now.isAfter(auction.getEndsAt())) {
            throw new AuctionAlreadyEndedException(auction.getId());
        }

        auction.setCurrentPrice(price);
        auction.setWinnerEmail(winnerEmail);
        auction.setWinnerId(winnerId);

        return auctionRepository.save(auction);
    }

    @Override
    public Auction requireById(int id) {
        return auctionRepository.requireById(id);
    }

    private Auction toAuction(String name, LocalDateTime startsAt, String description, BigDecimal price, int userId) {
        var auction = new Auction();
        auction.setEndsAt(startsAt.plusHours(3));
        auction.setDescription(description);
        auction.setStartPrice(price);
        auction.setName(name);
        auction.setStartsAt(startsAt);
        auction.setCurrentPrice(price);
        auction.setOwnerId(userId);
        return auction;
    }
}
