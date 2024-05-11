package org.example.exception;

import org.example.exception.generic.BadRequestException;

public class AuctionPriceException extends BadRequestException {
    public AuctionPriceException(Integer id, Double price) {
        super("For auction (id: %s), provided price: %s was lower or equal with current auction price"
                .formatted(id, price));
    }
}
