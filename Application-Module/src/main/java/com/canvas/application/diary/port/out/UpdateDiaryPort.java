package com.canvas.application.diary.port.out;

import com.canvas.domain.diary.entity.Diary;

public interface UpdateDiaryPort {
    void update(Diary diary);
}
