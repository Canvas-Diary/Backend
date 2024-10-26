package com.canvas.common.page;

public record PageRequest(
        Integer page,
        Integer size,
        Sort sort
) {
}
