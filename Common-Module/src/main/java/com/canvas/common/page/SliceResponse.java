package com.canvas.common.page;

import java.util.List;

public record SliceResponse<T>(
        List<T> content,
        Integer size,
        Integer number,
        Boolean hasNext
) {
}
