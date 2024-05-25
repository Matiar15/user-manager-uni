package org.example.auction;

import com.querydsl.core.types.Predicate;
import org.example.Auction;
import org.example.QAuction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuctionCustomRepository {
    Page<Auction> fetchByFilter(AuctionFilter filter, Pageable page);

    Predicate asPredicate(QAuction root, AuctionFilter filter);
}
