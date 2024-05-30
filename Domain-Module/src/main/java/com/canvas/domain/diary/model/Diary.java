package com.canvas.domain.diary.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // UUID, v4 난수 -> v7 시간순으로 만들어줌
    @Column(name = "diary_id")
    private Long id;

    @Column(length = 1000)
    private String content;

    @Enumerated(EnumType.STRING)
    private Emotion emotion;
    private String imageUrl;
    private LocalDate date;

    private Diary(String content, Emotion emotion, String imageUrl) {
        this.content = content;
        this.emotion = emotion;
        this.imageUrl = imageUrl;
        this.date = LocalDate.now();
    }

    public static Diary of(String content, Emotion emotion, String imageUrl) {
        return new Diary(content, emotion, imageUrl);
    }

}
