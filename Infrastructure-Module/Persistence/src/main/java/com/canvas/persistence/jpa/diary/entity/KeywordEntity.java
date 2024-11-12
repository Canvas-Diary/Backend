package com.canvas.persistence.jpa.diary.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "keyword")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KeywordEntity {

    @Id
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "keywordEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaryKeywordEntity> diaryKeywordEntities = new ArrayList<>();

}
