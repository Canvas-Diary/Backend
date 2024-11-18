package com.canvas.persistence.jpa.diary.entity;

import com.canvas.persistence.jpa.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "diary_keyword")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DiaryKeywordEntity extends BaseEntity {

    @Id
    private UUID id;

    @Column(name = "diary_id", nullable = false)
    private UUID diaryId;

    @Column(name = "keyword_id", nullable = false)
    private UUID keywordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id", insertable = false, updatable = false)
    private KeywordEntity keywordEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", insertable = false, updatable = false)
    private DiaryEntity diaryEntity;

    public DiaryKeywordEntity(UUID id, UUID diaryId, UUID keywordId) {
        this.id = id;
        this.diaryId = diaryId;
        this.keywordId = keywordId;
    }
}
