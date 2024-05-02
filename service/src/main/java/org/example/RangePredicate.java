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
        return field.between(range.lowerEndpoint(), range.upperEndpoint());
    }


    public static <T extends Comparable<?>> Predicate matches(
            Range<T> range,
            DatePath<T> field
    ) {
        return field.between(range.lowerEndpoint(), range.upperEndpoint());
    }
}
