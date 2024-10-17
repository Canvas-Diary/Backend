package com.canvas.persistence.jpa.diary.adapter;

import com.canvas.application.image.port.out.ImageManagementPort;
import com.canvas.domain.diary.entity.Image;
import com.canvas.persistence.jpa.diary.ImageMapper;
import com.canvas.persistence.jpa.diary.entity.ImageEntity;
import com.canvas.persistence.jpa.diary.repository.ImageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageManagementJpaAdapter implements ImageManagementPort {

    private final ImageJpaRepository imageJpaRepository;

    @Override
    public Image save(Image image) {
        ImageEntity imageEntity = imageJpaRepository.save(ImageMapper.toEntity(image));
        return ImageMapper.toDomain(imageEntity);
    }

    @Override
    public void delete(Image image) {
        imageJpaRepository.deleteById(image.getId());
    }

}
