package org.example.exception;

import org.example.exception.generic.BadRequestException;

import java.time.LocalDateTime;

public class StartsAtExpiredException extends BadRequestException {
    public StartsAtExpiredException(LocalDateTime startsAt) {
        super("Starts at property provided is before current date. Date: %s".formatted(startsAt));
    }
}
