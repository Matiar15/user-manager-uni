package org.example.exception;

import org.example.exception.generic.BadRequestException;

public class AuctionNotStartedException extends BadRequestException {
    public AuctionNotStartedException(Integer id) {
        super("Auction with id: %s hasn't been started yet".formatted(id));
    }
}
