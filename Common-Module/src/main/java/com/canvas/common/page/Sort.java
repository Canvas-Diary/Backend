package com.canvas.common.page;

import java.util.ArrayList;
import java.util.List;

public record Sort(
        List<Order> orders
) {

    public static Sort by(Direction direction, String property) {
        return new Sort(List.of(new Order(direction, property)));
    }

    public Sort and(Sort sort) {
        List<Order> newOrders = new ArrayList<>(orders);
        newOrders.addAll(sort.orders);

        return new Sort(newOrders);
    }

    public record Order(
            Direction direction,
            String property
    ) { }

    public enum Direction {
        ASC, DESC
    }
}
