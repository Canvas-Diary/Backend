package com.canvas.domain.diary.entity;

import com.canvas.domain.common.DomainId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Image {
    private DomainId id;
    private DomainId diaryId;
    private Boolean isMain;
    private String s3Uri;
}
