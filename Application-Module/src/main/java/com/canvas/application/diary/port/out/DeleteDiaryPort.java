package com.canvas.application.diary.port.out;

import com.canvas.domain.diary.entity.Diary;

public interface DeleteDiaryPort {
    void delete(Diary diary);
}
