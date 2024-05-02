package org.example.auction;

import com.querydsl.core.types.Predicate;
import org.example.Auction;
import org.example.QAuction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Integer>, AuctionCustomRepository {
}
