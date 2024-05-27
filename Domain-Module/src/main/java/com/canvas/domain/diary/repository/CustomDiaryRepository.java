package com.canvas.domain.diary.repository;

import com.canvas.domain.diary.model.Diary;
import com.canvas.domain.diary.model.Emotion;

import java.time.LocalDate;
import java.util.List;

public interface CustomDiaryRepository {

    List<Diary> findAllByDateRangeAndContentAndEmotion(LocalDate from, LocalDate to, String content, Emotion emotion);

}
