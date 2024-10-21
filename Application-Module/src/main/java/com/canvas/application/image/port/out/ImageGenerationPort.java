package com.canvas.application.image.port.out;

import com.canvas.application.common.enums.Style;

public interface ImageGenerationPort {
    String generate(String prompt, Style style);
}
