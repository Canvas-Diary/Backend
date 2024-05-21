package com.canvas.api.diary.application.dto;

import com.canvas.domain.diary.model.Diary;
import com.canvas.domain.diary.model.Emotion;

import java.time.LocalDate;
import java.util.List;

public class DiarySearch {

    public record Request(
            LocalDate from,
            LocalDate to,
            String content,
            Emotion emotion
    ){

    }

    public record Response(
            List<DiaryResponse> diaries
    ) {

        record DiaryResponse(
                String content,
                String imageUrl,
                Emotion emotion,
                LocalDate date
        ) {

            private static DiaryResponse from(Diary diary) {
                return new DiaryResponse(diary.getContent(), diary.getImageUrl(), diary.getEmotion(), diary.getDate());
            }
        }
    }

    public static Response convertToDiaryReadResponse(List<Diary> diaries) {
        return new Response(diaries.stream().map(Response.DiaryResponse::from).toList());
    }

    public static Request convertToRequest(
            LocalDate from,
            LocalDate to,
            String content,
            Emotion emotion
    ) {
        return new Request(from, to, content, emotion);
    }
}
