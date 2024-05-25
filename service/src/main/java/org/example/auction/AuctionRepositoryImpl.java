package org.example.auction;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.example.Auction;
import org.example.QAuction;
import org.example.RangePredicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.Optional;

public class AuctionRepositoryImpl extends QuerydslRepositorySupport implements AuctionCustomRepository {
    public AuctionRepositoryImpl() {
        super(Auction.class);
    }

    @Override
    public Page<Auction> fetchByFilter(AuctionFilter filter, Pageable page) {
        var root = QAuction.auction;

        var query = from(root)
                .where(asPredicate(root, filter));

        query = getQuerydsl().applyPagination(page, query);
        return PageableExecutionUtils.getPage(query.fetch(), page, query::fetchCount);
    }

    @Override
    public Predicate asPredicate(QAuction root, AuctionFilter filter) {
        if (filter == null) {
            return null;
        }

        var builder = new BooleanBuilder();

        builder.and(Optional.ofNullable(filter.name()).map(root.name::in).orElse(null))
                .and(RangePredicate.matches(filter.startsAt(), root.startsAt))
                .and(RangePredicate.matches(filter.endsAt(), root.endsAt))
                .and(Optional.ofNullable(filter.ownerId()).map(root.ownerId::in).orElse(null));

        return builder.getValue();
    }
}
