package org.example.exception;

import org.example.exception.generic.NotFoundException;

public class AuctionNotFoundException extends NotFoundException {
    private final int id;
    public AuctionNotFoundException(int id) {
        super("Did not find any Auction with ID: %s".formatted(id));
        this.id = id;
    }
}
