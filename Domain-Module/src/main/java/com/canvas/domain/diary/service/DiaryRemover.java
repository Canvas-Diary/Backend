package com.canvas.domain.diary.service;

import com.canvas.domain.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryRemover {
    private final DiaryRepository diaryRepository;


    public void removeDiary(Long diaryId) {
        diaryRepository.findById(diaryId)
                .ifPresent(diaryRepository::delete);
    }
}
