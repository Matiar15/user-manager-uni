package org.example.exception;

import org.example.exception.generic.BadRequestException;

public class AuctionAlreadyStartedException extends BadRequestException {
    public AuctionAlreadyStartedException(int id) {
        super("Could not update auction with id: %s, because it has already started".formatted(id));
    }
}
