package org.example.util;

import com.google.common.collect.Range;

import java.time.LocalDateTime;

import static com.google.common.collect.Range.*;

public abstract class RangeDto<T extends Comparable<?>> {
    private T from;
    private T to;

    public Range<T> asRange() {
        var range = all();
        if (from != null) range = atLeast(from);
        if (to != null) range = atMost(to);
        if (from != null && to != null) range = open(from, to);
        return (Range<T>) range;
    }

    public RangeDto(T from, T to) {
        this.from = from;
        this.to = to;
    }

    public T getFrom() {
        return from;
    }

    public T getTo() {
        return to;
    }

    public void setFrom(T from) {
        this.from = from;
    }

    public void setTo(T to) {
        this.to = to;
    }

    public static final class DateTimeRangeDto extends RangeDto<LocalDateTime> {
        public DateTimeRangeDto(LocalDateTime from, LocalDateTime to) {
            super(from, to);
        }
    }
}