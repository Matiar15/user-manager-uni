package org.example.exception;

import org.example.exception.generic.BadRequestException;

public class AuctionAlreadyEndedException extends BadRequestException {
    public AuctionAlreadyEndedException(Integer id) {
        super("Auction with id: %s had already been ended".formatted(id));
    }
}
