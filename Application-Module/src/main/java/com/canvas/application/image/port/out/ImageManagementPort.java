package com.canvas.application.image.port.out;

import com.canvas.domain.diary.entity.Image;

public interface ImageManagementPort {
    Image save(Image image);
    void delete(Image image);
}
