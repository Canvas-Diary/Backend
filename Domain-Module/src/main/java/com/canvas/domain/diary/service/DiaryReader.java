package com.canvas.domain.diary.service;

import com.canvas.domain.diary.model.Diary;
import com.canvas.domain.diary.model.Emotion;
import com.canvas.domain.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryReader {
    private final DiaryRepository diaryRepository;


    public List<Diary> readByDateRangeAndContentAndEmotion(
            LocalDate from, LocalDate to, String content, Emotion emotion
    ) {
        return diaryRepository.findAllByDateRangeAndContentAndEmotion(from, to, content, emotion);
    }
}
