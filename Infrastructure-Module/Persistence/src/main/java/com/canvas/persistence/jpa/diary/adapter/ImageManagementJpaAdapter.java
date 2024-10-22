package com.canvas.persistence.jpa.diary.adapter;

import com.canvas.application.image.port.out.ImageManagementPort;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Image;
import com.canvas.persistence.jpa.diary.ImageMapper;
import com.canvas.persistence.jpa.diary.entity.ImageEntity;
import com.canvas.persistence.jpa.diary.repository.ImageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<Image> saveAll(List<Image> images) {
        List<ImageEntity> imageEntities = imageJpaRepository.saveAll(
                images.stream()
                        .map(ImageMapper::toEntity)
                        .toList());

        return imageEntities.stream()
                .map(ImageMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByIdAndUserId(DomainId imageId, DomainId userId) {
        return imageJpaRepository.existsByIdAndWriterId(imageId.value(), userId.value());
    }

    @Override
    public List<Image> getAllInDiaryByIdAndWriterId(DomainId ImageId, DomainId userId) {
        return imageJpaRepository.findAllInDiaryByIdAndWriterId(ImageId.value(), userId.value()).stream()
                .map(ImageMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(DomainId imageId) {
        imageJpaRepository.deleteById(imageId.value());
    }

}
