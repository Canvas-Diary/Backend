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

    public static Image withoutId(DomainId diaryId, Boolean isMain, String s3Uri) {
        return new Image(DomainId.generate(), diaryId, isMain, s3Uri);
    }

    public static Image withId(DomainId domainId, DomainId diaryId, Boolean isMain, String s3Uri) {
        return new Image(domainId, diaryId, isMain, s3Uri);
    }
}
