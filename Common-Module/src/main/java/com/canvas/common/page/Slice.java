package com.canvas.common.page;

import java.util.List;

public record Slice<T>(
        List<T> content,
        Integer size,
        Integer number,
        Boolean hasNext
) {
    public static <T> Slice<T> of(
            List<T> content,
            Integer size,
            Integer number,
            Boolean hasNext
    ) {
        return new Slice<>(content, size, number, hasNext);
    }
}
