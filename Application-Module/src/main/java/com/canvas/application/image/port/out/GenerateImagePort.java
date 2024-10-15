package com.canvas.application.image.port.out;

import com.canvas.application.common.enums.Style;
import com.canvas.domain.diary.entity.Image;

public interface GenerateImagePort {
    Image generate(String content, Style style);
}
