package com.canvas.persistence.jpa.common;


import com.canvas.common.page.PageRequest;
import com.canvas.common.page.Slice;
import com.canvas.common.page.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import java.util.function.Function;

import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.by;

public class PageMapper {
    public static org.springframework.data.domain.PageRequest toJpaPageRequest(PageRequest pageRequest) {
        return of(pageRequest.page(), pageRequest.size(), toJpaSort(pageRequest.sort()));
    }

    private static org.springframework.data.domain.Sort toJpaSort(Sort sort) {
        return by(sort.orders().stream()
                        .map(order -> new Order(
                                Direction.valueOf(order.direction().name()),
                                order.property()))
                        .toList());
    }

    public static <T, S> Slice<T> toDomainSlice(org.springframework.data.domain.Slice<S> slice, Function<S, T> toDomain) {
        var mappedSlice = slice.map(toDomain);
        return Slice.of(
                mappedSlice.getContent(),
                mappedSlice.getSize(),
                mappedSlice.getNumber(),
                mappedSlice.hasNext()
        );
    }
}
