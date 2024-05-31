package com.canvas.domain.diary.service;

import com.canvas.domain.diary.model.Diary;
import com.canvas.domain.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryCreator {

    private final DiaryRepository diaryRepository;

    public void diarySave(Diary diary) {
        diaryRepository.save(diary);
    }

}
