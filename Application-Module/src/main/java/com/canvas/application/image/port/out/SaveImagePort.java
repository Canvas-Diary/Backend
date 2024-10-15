package com.canvas.application.image.port.out;

import com.canvas.domain.diary.entity.Image;

public interface SaveImagePort {
    Image save(Image image);
}
