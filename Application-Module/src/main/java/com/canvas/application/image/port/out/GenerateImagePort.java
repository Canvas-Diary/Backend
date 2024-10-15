package com.canvas.application.image.port.out;

import com.canvas.application.common.enums.Style;

public interface GenerateImagePort {
    String generate(String content, Style style);
}
