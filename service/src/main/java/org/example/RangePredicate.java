package org.example;

import com.google.common.collect.Range;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.DateTimePath;

public class RangePredicate {
    public static <T extends Comparable<?>> Predicate matches(
            Range<T> range,
            DateTimePath<T> field
    ) {
        if (range == null) return null;
        var upperBound = range.hasUpperBound() ? range.upperEndpoint() : null;
        var lowerBound = range.hasLowerBound() ? range.lowerEndpoint() : null;
        return field.between(lowerBound, upperBound);
    }


    public static <T extends Comparable<?>> Predicate matches(
            Range<T> range,
            DatePath<T> field
    ) {
        if (range == null) return null;
        var upperBound = range.hasUpperBound() ? range.upperEndpoint() : null;
        var lowerBound = range.hasLowerBound() ? range.lowerEndpoint() : null;
        return field.between(lowerBound, upperBound);
    }
}
