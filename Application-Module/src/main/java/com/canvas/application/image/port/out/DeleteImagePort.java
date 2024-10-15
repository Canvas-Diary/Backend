package com.canvas.application.image.port.out;

import com.canvas.domain.diary.entity.Image;

public interface DeleteImagePort {
    void delete(Image image);
}
