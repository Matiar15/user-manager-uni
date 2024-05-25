package org.example.auction;

import org.example.Auction;
import org.example.exception.AuctionNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Integer>, AuctionCustomRepository {
    default Auction requireById(int id) {
        return findById(id).orElseThrow(() -> new AuctionNotFoundException(id));
    }
}
