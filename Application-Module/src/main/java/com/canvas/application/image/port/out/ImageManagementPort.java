package com.canvas.application.image.port.out;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Image;

import java.util.List;

public interface ImageManagementPort {
    Image save(Image image);
    List<Image> saveAll(List<Image> images);
    boolean existsByIdAndUserId(DomainId imageId, DomainId userId);
    List<Image> getAllInDiaryByIdAndWriterId(DomainId imageId, DomainId userId);
    void deleteById(DomainId imageId);
}
